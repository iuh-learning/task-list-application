package fit.android.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fit.android.app.R;
import fit.android.app.dao.ItemDetailListDAO;
import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.fragment.FragmentDetailTaskList;
import fit.android.app.helper.Message;
import fit.android.app.helper.MessageBoxListener;
import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;

public class MainActivity_DetailList extends AppCompatActivity {

    // init
    private TextView tvNameTask;
    private TextView tvEdit;
    private TextView tvBackToList;
    private Button btnADD;
    private Button btnUPDATE;

    private AppDatabase db;
    private ItemDetailListDAO dao;
    private ItemTaskListDAO itemTaskListDAO;
    private UserDAO userDAO;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_detail);

        // SQLite
        db = AppDatabase.getDatabase(MainActivity_DetailList.this);

        // DAO
        dao = db.itemDetailListDAO();
        itemTaskListDAO = db.itemTaskListDAO();
        userDAO = db.userDAO();

        // find id
        tvNameTask = findViewById(R.id.txtNameTask);
        tvEdit = findViewById(R.id.txtEdit);
        tvBackToList = findViewById(R.id.txtBackToList);
        btnUPDATE = findViewById(R.id.btnUPDATE);
        btnADD = findViewById(R.id.btnADD);

        // Get id from page Task List
        Intent intent = getIntent();
        int taskID = intent.getIntExtra("id_task", -1);
        String statusDel = intent.getStringExtra("status");

        //Get mail
        String mail = intent.getStringExtra("user_email");

        //get data
        if(dao.getAll(taskID).isEmpty()) {
            getDataUserFromFirebaseSaveToRoomDatabase(taskID, mail);
        }

        // Get name
        ItemDetailList itemDetailList = getNameTaskFromIntent();
        if (itemDetailList != null) {
            tvEdit.setText(itemDetailList.getNameDetail());
        }

        //set name detail task after click item
        String nameTask = itemTaskListDAO.findByIdTask(taskID).getNameTask();
        tvNameTask.setText(nameTask);

        if(statusDel != null && statusDel.equals("del")) {{
            saveDataTaskDetailFromClientToFireBase(taskID, mail);
        }}

        // Render --> UI Listview (reload listview)
        reLoadListView();

        //App event handle
        //add
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEdit = tvEdit.getText().toString().trim();

                // check null
                if (txtEdit.isEmpty()) {
                    Message.showMessage(MainActivity_DetailList.this, "Message", "Task detail not null!");
                    return;
                }

                // check name detail
                if (dao.findByNameTask(txtEdit) != null) {
                    Message.showMessage(MainActivity_DetailList.this, "Message", "Task detail already exists!");
                    return;
                }
                dao.insert(new ItemDetailList(txtEdit, taskID));
                saveDataTaskDetailFromClientToFireBase(taskID, mail);
                reLoadListView();
                clearInput();
            }
        });


        //Update
        btnUPDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEdit = tvEdit.getText().toString().trim();

                // check null
                if (txtEdit.isEmpty()) {
                    Message.showMessage(MainActivity_DetailList.this, "Message", "Task detail not null!");
                    return;
                }

                // check name detail
                if (dao.findByNameTask(txtEdit) != null) {
                    Message.showMessage(MainActivity_DetailList.this, "Message", "Task detail already exists!");
                    return;
                }

                Message.showConfirmMessgae(MainActivity_DetailList.this, "Message", "Do you want to update?", new MessageBoxListener() {
                    @Override
                    public void result(int result) {
                        if (result == 1) {
                            itemDetailList.setNameDetail(txtEdit);
                            dao.update(itemDetailList);
                            reLoadListView();
                            saveDataTaskDetailFromClientToFireBase(taskID, mail);
                            clearInput();
                        }
                    }
                });
            }
        });

        tvBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity_DetailList.this, MainActivity_TaskList.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_email", mail);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

    }

    private void clearInput() {
        tvEdit.setText("");
        tvEdit.requestFocus();
    }

    // reload listview
    public void reLoadListView() {
        FragmentDetailTaskList fragmentDetailTaskList = new FragmentDetailTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.Fragment_detail, fragmentDetailTaskList, "Fragment item task-detail.")
                .commitAllowingStateLoss();
    }

    // get id task detail from intent
    private ItemDetailList getNameTaskFromIntent() {
        Bundle bundle = getIntent().getExtras();
        int taskID = bundle.getInt("id_taskDetail");

        ItemDetailList itemDetailList = dao.findByIDTask(taskID);

        return itemDetailList;
    }

    // save data to firebase
    private void saveDataTaskDetailFromClientToFireBase(int taskID, String mail) {
        List<ItemDetailList> list = dao.getAll(taskID);
        Map<String, ItemDetailList> mapItemDetails = new HashMap<>();

        int idUser = userDAO.findByEmail(mail).getId();
        String nameTask = itemTaskListDAO.findByIdTask(taskID).getNameTask();

        mDatabase = FirebaseDatabase.getInstance().getReference("/users/"+ idUser +"/list-task/"+ taskID + "/" + nameTask);

        for (ItemDetailList item : list) {
            mapItemDetails.put("" + item.getId(), item);
        }

        try {
            mDatabase.child("detail-task").setValue(mapItemDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getDataUserFromFirebaseSaveToRoomDatabase(int taskID, String email) {
        int userId = userDAO.findByEmail(email).getId();
        String nameTask = itemTaskListDAO.findByIdTask(taskID).getNameTask();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/" + userId + "/list-task/" + taskID + "/" + nameTask);

        mDatabase.child("/detail-task").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    ItemDetailList itemDetailList = sn.getValue(ItemDetailList.class);
                    try{
                        dao.insert(itemDetailList);
                    }catch (Exception e) {
                        Log.e("ERROR:", "INSERT FAIL");
                        e.printStackTrace();
                    }
                }

                reLoadListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("ERROR: ", "loadPost:onCancelled", error.toException());
            }
        });
    }


}

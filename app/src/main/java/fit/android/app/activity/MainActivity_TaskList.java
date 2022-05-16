package fit.android.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fit.android.app.dao.ItemDetailListDAO;
import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.fragment.FragmentItemTaskList;
import fit.android.app.R;
import fit.android.app.helper.Message;
import fit.android.app.helper.MessageBoxListener;
import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;
import fit.android.app.model.User;

public class MainActivity_TaskList extends AppCompatActivity {

    // init
    private Button btnAdd, btnUpdate;
    private EditText edtTask;
    private TextView tvName, tvLogout;

    private AppDatabase db;
    private ItemTaskListDAO dao;
    private UserDAO userDAO;
    private ItemDetailListDAO itemDetailListDAO;
    // Firebase
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_list);

        // SQLite
        db = AppDatabase.getDatabase(MainActivity_TaskList.this);

        // DAO
        dao = db.itemTaskListDAO();
        userDAO = db.userDAO();
        itemDetailListDAO = db.itemDetailListDAO();

        // find id
        btnAdd = findViewById(R.id.btnAdd);
        edtTask = findViewById(R.id.edtTask);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvName = findViewById(R.id.txtName);
        tvLogout = findViewById(R.id.tvLogout);

        // get name from TaskList Apdapter
        ItemTaskList itemTaskList = getNameTaskFromIntent();
        if(itemTaskList != null) {
            edtTask.setText(itemTaskList.getNameTask());
        }

        // get email from MainActivity_Login
        Intent intent = getIntent();
        String emailFromLogin = intent.getStringExtra("user_email");
        String statusDel = intent.getStringExtra("status");
        if(statusDel != null && statusDel.equals("del")) {{
            reloadDataFromClientToFireBase(emailFromLogin);
        }}

        //get data from firebase
        if(dao.getAll(emailFromLogin).isEmpty()) {
            getDataUserFromFirebaseSaveToRoomDatabase(emailFromLogin);
        }

        // Render --> UI Listview (reload listview)
        reLoadListView();

        // get fullname
        UserDAO userDao = AppDatabase.getDatabase(this).userDAO();
        String fullName = userDao.findByEmail(emailFromLogin).getFullName();

        // set Textview name
        tvName.setText("Hi, " + fullName);

        // Add task
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtNameTask = edtTask.getText().toString().trim();

                // check null
                if(edtNameTask.isEmpty()) {
                    Message.showMessage(MainActivity_TaskList.this, "Message", "Task name not null!");
                    return;
                }
                // check name task
                if(dao.findByNameTask(edtNameTask) != null) {
                    Message.showMessage(MainActivity_TaskList.this, "Message", "Task name already exists!");
                    return;
                }

                dao.insert(new ItemTaskList(edtNameTask, emailFromLogin));
                reloadDataFromClientToFireBase(emailFromLogin);
                reLoadListView();
                clearInput();
            }
        });

        // Update task
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtNameTask = edtTask.getText().toString().trim();

                // check null
                if(edtNameTask.isEmpty()) {
                    Message.showMessage(MainActivity_TaskList.this, "Message", "Task name not null!");
                    return;
                }
                // check task name
                if(dao.findByNameTask(edtNameTask) != null) {
                    Message.showMessage(MainActivity_TaskList.this, "Message", "Task name already exists. Updated fail!");
                    return;
                }

                Message.showConfirmMessgae(MainActivity_TaskList.this, "Message", "Do you want to update?", new MessageBoxListener() {
                    @Override
                    public void result(int result) {
                        if(result == 1) {
                            if(itemTaskList == null) {
                                Message.showMessage(MainActivity_TaskList.this, "Message", "Task name not exist. Do you want add task?");
                                return;
                            }
                            itemTaskList.setNameTask(edtNameTask);
                            dao.update(itemTaskList);
                            reloadDataFromClientToFireBase(emailFromLogin);
                            reLoadListView();
                            clearInput();
                        }
                    }
                });
            }
        });

        // Button Logout
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message.showConfirmMessgae(MainActivity_TaskList.this, "Message", "Do you want to logout account?", new MessageBoxListener() {
                    @Override
                    public void result(int result) {
                        if(result == 1) {
                            Intent intent = new Intent(MainActivity_TaskList.this, MainActivity_Login.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity_TaskList.this, "Logout successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    // reload listview
    public void reLoadListView() {
        FragmentItemTaskList fragmentItemTaskList = new FragmentItemTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.idFrameLayoutTaskList, fragmentItemTaskList, "Fragment item task-list.")
                .commitAllowingStateLoss();
    }

    // get name task from intent
    private ItemTaskList getNameTaskFromIntent() {
        Intent intent = getIntent();
        String nameTask = intent.getStringExtra("name_task");

        ItemTaskList itemTaskList = dao.findByNameTask(nameTask);

        return  itemTaskList;
    }

    // clear input
    private void clearInput() {
        edtTask.setText("");
        edtTask.requestFocus();
    }

    // save data to firebase
    private void reloadDataFromClientToFireBase(String email) {
        List<ItemTaskList> list = dao.getAll(email);
        Map<String, ItemTaskList> mapTasks = new HashMap<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        int id = userDAO.findByEmail(email).getId();

        for (ItemTaskList item : list) {
            mapTasks.put("" + item.getId(), item);
        }

        try {
            mDatabase.child(id + "").child("list-task").setValue(mapTasks);

        }catch (Exception e) {
            e.printStackTrace();
        }

        for(ItemTaskList item : list) {
            saveDataTaskDetailFromClientToFireBase(item.getId(), email);
        }
    }

    private void saveDataTaskDetailFromClientToFireBase(int taskID, String mail) {
        try{
            List<ItemDetailList> list = itemDetailListDAO.getAll(taskID);
            Map<String, ItemDetailList> mapUsers = new HashMap<>();

            if(list != null) {
                int idUser = userDAO.findByEmail(mail).getId();
                String nameTask = dao.findByIdTask(taskID).getNameTask();

                mDatabase = FirebaseDatabase.getInstance().getReference("/users/" + idUser + "/list-task/" + taskID + "/" + nameTask);

                for (ItemDetailList item : list) {
                    mapUsers.put("" + item.getId(), item);
                }

                try {
                    mDatabase.child("detail-task").setValue(mapUsers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception ex) {

        }
    }

    private void getDataUserFromFirebaseSaveToRoomDatabase(String email) {
        int userId = userDAO.findByEmail(email).getId();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mDatabase.child(userId + "/list-task").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    ItemTaskList itemTaskList = sn.getValue(ItemTaskList.class);
                    try{
                        dao.insert(itemTaskList);
                    }catch (Exception e) {
                        Log.e("ERROR:", "INSERT FAIL");
                        e.printStackTrace();
                    }
                }

                //get data success then load listview now
                reLoadListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("ERROR: ", "loadPost:onCancelled", error.toException());
            }
        });
    }
}
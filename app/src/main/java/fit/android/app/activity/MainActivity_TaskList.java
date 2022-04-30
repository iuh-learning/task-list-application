package fit.android.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.fragment.FragmentItemTaskList;
import fit.android.app.R;
import fit.android.app.helper.Message;
import fit.android.app.helper.MessageBoxListener;
import fit.android.app.model.ItemTaskList;

public class MainActivity_TaskList extends AppCompatActivity {

    // init
    private Button btnAdd, btnUpdate;
    private EditText edtTask;
    private TextView tvLogout;

    private AppDatabase db;
    private ItemTaskListDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_list);

        // Render --> UI Listview (reload listview)
        reLoadListView();

        // SQLite
        db = AppDatabase.getDatabase(MainActivity_TaskList.this);

        // DAO
        dao = db.itemTaskListDAO();

        // find id
        btnAdd = findViewById(R.id.btnAdd);
        edtTask = findViewById(R.id.edtTask);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvLogout = findViewById(R.id.tvLogout);

        // Nhận name from TaskListApdapter
        ItemTaskList itemTaskList = getNameTaskFromIntent();
        if(itemTaskList != null) {
            edtTask.setText(itemTaskList.getNameTask());
        }

        // Nhận email from MainActivity_Login
        Intent intent = getIntent();
        String emailFromLogin = intent.getStringExtra("user_email");

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
                reLoadListView();
                clearInput();
                Message.showMessage(MainActivity_TaskList.this, "Message", "Inserted task name successfully.");
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
                            itemTaskList.setNameTask(edtNameTask);
                            dao.update(itemTaskList);
                            reLoadListView();
                            clearInput();
                            Message.showMessage(MainActivity_TaskList.this, "Message", "Updated successfully.");
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
                .commit();
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

}
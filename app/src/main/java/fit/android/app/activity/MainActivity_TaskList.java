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
import android.widget.Toast;

import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.fragment.FragmentItemTaskList;
import fit.android.app.R;
import fit.android.app.model.ItemTaskList;

public class MainActivity_TaskList extends AppCompatActivity {

    // init
    private Button btnAdd, btnUpdate;
    private EditText edtTask;

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

        // Nhận
        ItemTaskList itemTaskList = getItemTaskFromIntent();
        if(itemTaskList != null) {
            edtTask.setText(itemTaskList.getNameTask());
        }

        // Add task
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtNameTask = edtTask.getText().toString().trim();

                if(edtNameTask.isEmpty()) {
                    Toast.makeText(MainActivity_TaskList.this, "Bạn cần phải nhập tên công việc!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dao.insert(new ItemTaskList(edtNameTask, "baotran@gmail.com"));
                reLoadListView();

                Toast.makeText(MainActivity_TaskList.this, "Thành công." + edtNameTask, Toast.LENGTH_SHORT).show();
            }
        });

        // Update task
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String
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

    // get item task from intent
    private ItemTaskList getItemTaskFromIntent() {
        Intent intent = getIntent();
        String nameTask = intent.getStringExtra("name_task");

        ItemTaskList itemTaskList = dao.findByNameTask(nameTask);

        return  itemTaskList;
    }
}
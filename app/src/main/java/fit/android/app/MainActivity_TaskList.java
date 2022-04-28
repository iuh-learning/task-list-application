package fit.android.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity_TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_list);

        // Render --> UI Listview (reload listview)
        FragmentItemTaskList fragmentItemTaskList = new FragmentItemTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.idFrameLayoutTaskList, fragmentItemTaskList, "Fragment item task-list")
                .commit();
    }
}
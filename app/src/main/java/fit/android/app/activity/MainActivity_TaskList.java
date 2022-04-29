package fit.android.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fit.android.app.fragment.FragmentItemTaskList;
import fit.android.app.R;

public class MainActivity_TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_list);

        // Render --> UI Listview (reload listview)
        FragmentItemTaskList fragmentItemTaskList = new FragmentItemTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.idFrameLayoutTaskList, fragmentItemTaskList, "Fragment item task-list.")
                .commit();
    }
}
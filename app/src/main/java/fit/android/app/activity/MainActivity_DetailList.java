package fit.android.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fit.android.app.R;
import fit.android.app.fragment.FragmentDetailTaskList;
import fit.android.app.fragment.FragmentItemTaskList;

public class MainActivity_DetailList extends AppCompatActivity {

    // init
    private TextView tvNameTask;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_detail);

        // Render --> UI Listview (reload listview)
        FragmentDetailTaskList fragmentDetailTaskList = new FragmentDetailTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.Fragment_detail, fragmentDetailTaskList, "Fragment item Detail-list.")
                .commit();

        // find id
        tvNameTask = findViewById(R.id.textView3);

        // Nhận name task from page Task List
        Intent intent = getIntent();
        String nameTask = intent.getStringExtra("name_task");
        tvNameTask.setText(nameTask);
    }
}

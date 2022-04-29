package fit.android.app.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fit.android.app.R;
import fit.android.app.fragment.FragmentDetailTaskList;
import fit.android.app.fragment.FragmentItemTaskList;

public class MainActivity_DetailList extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_detail);

        // Render --> UI Listview (reload listview)
        FragmentDetailTaskList fragmentDetailTaskList = new FragmentDetailTaskList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.Fragment_detail, fragmentDetailTaskList, "Fragment item Detail-list.")
                .commit();
    }
}

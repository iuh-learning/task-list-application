package fit.android.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fit.android.app.R;
import fit.android.app.activity.MainActivity_DetailList;
import fit.android.app.activity.MainActivity_Register;
import fit.android.app.model.ItemTaskList;

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private int idLayout;
    private List<ItemTaskList> listItems;
    private int index = -1;

    // Constructor
    public TaskListAdapter(Context context, int idLayout, List<ItemTaskList> listItems) {
        this.context = context;
        this.idLayout = idLayout;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        if(listItems.size() != 0 && !listItems.isEmpty()) {
            return listItems.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout, viewGroup, false);
        }

        // find id
        TextView txtID = view.findViewById(R.id.txtID);
        TextView txtNameTask = view.findViewById(R.id.txtNameTask);
        Button btnNext = view.findViewById(R.id.btnNext);
        Button btnDel = view.findViewById(R.id.btnDelete);

        final ItemTaskList itemTaskList = listItems.get(i);

        if(listItems != null && !listItems.isEmpty()) {
            // set tv
            txtID.setText(String.valueOf(itemTaskList.getId()) + ". ");
            txtNameTask.setText(itemTaskList.getNameTask());
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity_DetailList.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}

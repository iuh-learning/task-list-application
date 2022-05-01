package fit.android.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fit.android.app.R;
import fit.android.app.activity.MainActivity_DetailList;
import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;

public class DetailListAdapter extends BaseAdapter {

    private Context context;
    private int idLayout;
    private List<ItemDetailList> listItems;
    private int index = -1;

    // Constructor
    public DetailListAdapter(Context context, int idLayout, List<ItemDetailList> listItems) {
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
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtID = view.findViewById(R.id.txtID);

        final ItemDetailList itemDetailList = listItems.get(i);

        if(listItems != null && !listItems.isEmpty()) {
            // set tv
            txtName.setText(String.valueOf(itemDetailList.getNameDetail()) + ". ");
            txtID.setText(String.valueOf(itemDetailList.getId()) + ". ");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity_DetailList.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id_taskDetail",listItems.get(i).getId());
                bundle.putInt("id_task",listItems.get(i).getId());
                Toast.makeText(context, listItems.get(i).getId()+"" + listItems.get(i).getNameDetail() + "", Toast.LENGTH_SHORT).show();

                intent.putExtras(bundle);

                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}

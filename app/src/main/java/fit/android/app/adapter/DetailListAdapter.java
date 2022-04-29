package fit.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fit.android.app.R;
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
        TextView txtID = view.findViewById(R.id.txtName);

        final ItemDetailList itemDetailList = listItems.get(i);

        if(listItems != null && !listItems.isEmpty()) {
            // set tv
            txtID.setText(String.valueOf(itemDetailList.getNameDetail()) + ". ");
        }

        return view;
    }
}

package fit.android.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fit.android.app.R;
import fit.android.app.adapter.DetailListAdapter;
import fit.android.app.adapter.TaskListAdapter;
import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;


public class FragmentDetailTaskList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDetailTaskList() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetailTaskList newInstance(String param1, String param2) {
        FragmentDetailTaskList fragment = new FragmentDetailTaskList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private DetailListAdapter adapter;
    private List<ItemDetailList> listItems;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_task_list, container, false);

        // find id
        listView = view.findViewById(R.id.listviewDetail);

        listItems = new ArrayList<>();
        listItems.add(new ItemDetailList(1, "Chở gái",1));
        listItems.add(new ItemDetailList(2, "Rút tiền",1));
        listItems.add(new ItemDetailList(3, "Mua thực phẩm chức năng ",1));

        adapter = new DetailListAdapter(getActivity(), R.layout.custom_item_detail_listview, listItems);
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}
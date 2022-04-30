package fit.android.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fit.android.app.R;
import fit.android.app.adapter.TaskListAdapter;
import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.model.ItemTaskList;

public class FragmentItemTaskList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentItemTaskList() {
        // Required empty public constructor
    }

    public static FragmentItemTaskList newInstance(String param1, String param2) {
        FragmentItemTaskList fragment = new FragmentItemTaskList();
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

    // init
    private TaskListAdapter adapter;
    private List<ItemTaskList> listItems;
    private ListView listView;

    private AppDatabase db;
    private ItemTaskListDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_task_list, container, false);

        // SQLite
        db = AppDatabase.getDatabase(getActivity());

        // DAO
        dao = db.itemTaskListDAO();

        // find id
        listView = view.findViewById(R.id.idListViewTaskList);

        // load data to listview
        loadDataToListView();

        // Click on listview
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "Listview", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    // load listview
    public void loadDataToListView() {
        listItems = dao.getAll("baotran@gmail.com");
        adapter = new TaskListAdapter(getActivity(), R.layout.custom_item_list_view, listItems);
        listView.setAdapter(adapter);
    }
}
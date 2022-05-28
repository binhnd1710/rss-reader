package com.example.btl.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.btl.config.SQLiteHelper;
import com.example.btl.models.FeedSourceModel;

import java.util.ArrayList;

public class SourceListFragment extends ListFragment {
    private ArrayList<FeedSourceModel> feedSourceModels;
    private SQLiteHelper sqLiteHelper;
    private int selected;

    public interface Listener {
        void sourceClicked(long id);
    }

    private Listener listener;

    public SourceListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arg = getArguments();
        String category;
        if (arg != null) {
            category = arg.getString("category");
        } else {
            category = "";
        }
        sqLiteHelper = new SQLiteHelper(getLayoutInflater().getContext());
        feedSourceModels = sqLiteHelper.getAllRecords(category);
        String[] name = new String[feedSourceModels.size()];
        String[] link = new String[feedSourceModels.size()];
        for (int i = 0; i < feedSourceModels.size(); i++) {
            name[i] = feedSourceModels.get(i).getName();
            link[i] = feedSourceModels.get(i).getUrl();
        }
        FeedListFragment.urls = link;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getLayoutInflater().getContext(),
                android.R.layout.simple_list_item_1, name);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onListItemClick(@NonNull ListView listView, @NonNull View itemView, int position,
                                long id) {
        if (listener != null) {
            listener.sourceClicked(id);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnCreateContextMenuListener(this);
        getListView().setOnItemLongClickListener((parent, view1, position, id) -> {
            selected = position;
            return false;
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    ContextMenu.ContextMenuInfo
                                            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {
            sqLiteHelper.deleteRecord(feedSourceModels.get(selected));
            requireActivity().getSupportFragmentManager().beginTransaction().detach(this).
                    attach(this).commit();
        }
        return true;
    }
}

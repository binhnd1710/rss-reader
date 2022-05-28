package com.example.btl.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.btl.R;
import com.example.btl.activities.DescriptionActivity;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedListFragment extends ListFragment {
    public static String[] urls;

    public interface Listener {
        void itemClicked(long id);
    }

    private Listener listener;

    public FeedListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Bundle arg = getArguments();
        String url = null;
        if (arg != null) {
            long id = arg.getLong("id");
            url = urls[(int) id];
        } else {
            Log.v("RSSLog", "No URL provided");
        }
        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                String[] titles = new String[list.size()];
                String[] link = new String[list.size()];
                String[] pubDate = new String[list.size()];
                String[] description = new String[list.size()];
                String[] img = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    titles[i] = list.get(i).getTitle();
                    link[i] = list.get(i).getLink();
                    pubDate[i] = list.get(i).getPubDate().toString();
                    description[i] = list.get(i).getDescription();
                    img[i] = list.get(i).getImage();
                }
                ArrayList<HashMap<String, String>> lists = new ArrayList<>();
                HashMap<String, String> item;
                for (int i = 0; i < list.size(); i++) {
                    item = new HashMap<>();
                    item.put("title", list.get(i).getTitle());
                    @SuppressLint("SimpleDateFormat") String dd = new SimpleDateFormat(
                            "MMM d yyyy, hh:mm").format(list.get(i).getPubDate());
                    item.put("date", dd);
                    lists.add(item);
                }
                SimpleAdapter adapter = new SimpleAdapter(getLayoutInflater().getContext(), lists,
                        R.layout.listview_layout, new String[]{"title", "date"}, new int[]{R.id.
                        text_title, R.id.text_date});
                setListAdapter(adapter);
                DescriptionActivity.title = titles;
                DescriptionActivity.description = description;
                DescriptionActivity.link = link;
                DescriptionActivity.pubDate = pubDate;
                DescriptionActivity.img = img;
            }

            @Override
            public void onError() {
                Log.v("RSSLog", "Something went wrong");
            }
        });
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
        if (listener != null) listener.itemClicked(id);
    }
}

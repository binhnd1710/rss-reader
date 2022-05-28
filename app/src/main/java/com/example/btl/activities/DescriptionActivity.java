package com.example.btl.activities;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.config.RegEx;

public class DescriptionActivity extends AppCompatActivity {
    RegEx regEx;
    public static String desc_text;
    public static String image1;
    public static String[] title;
    public static String[] description;
    public static String[] link;
    public static String[] pubDate;
    public static String[] img;
    public static final String ID = "id";
    public int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        id = (int) getIntent().getExtras().get(ID);
        regEx = new RegEx();
        TextView titles = (TextView) findViewById(R.id.title);
        titles.setText(title[id]);
        TextView pDate = (TextView) findViewById(R.id.pub_date);
        pDate.setText(pubDate[id]);
        desc_text = RegEx.replaceMatches(description[id]);
        if (img[id] == null) {
            image1 = RegEx.findImage(description[id]);
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        if (image1.equals("") || image1.equals("not found")) {
            imageView.getLayoutParams().height = 0;
        } else {
            Glide.with(this).load(image1).into(imageView);
        }
        TextView desc = (TextView) findViewById(R.id.description);
        if (desc_text.equals(""))
            desc.setText("No description provided... View full news by clicking the following " +
                    "button.");
        else desc.setText(desc_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void webView(View view) {
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra(WebviewActivity.EXTRA_URL, link[id]);
        startActivity(intent);
    }
}

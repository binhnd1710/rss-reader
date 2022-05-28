package com.example.btl.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl.models.FeedSourceModel;
import com.example.btl.MainActivity;
import com.example.btl.R;
import com.example.btl.config.SQLiteHelper;

public class AddSourceActivity extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    FeedSourceModel feedSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        sqLiteHelper = new SQLiteHelper(this);
    }

    public void addSource(View view) {
        EditText name_input = (EditText) findViewById(R.id.edit_name);
        EditText url_input = (EditText) findViewById(R.id.edit_url);
        Spinner categories = (Spinner) findViewById(R.id.categories);
        String name = name_input.getText().toString();
        String url = url_input.getText().toString();
        String category = String.valueOf(categories.getSelectedItemId());
        if (!(name.equals("")) && !(url.equals(""))) {
            feedSourceModel = new FeedSourceModel();
            feedSourceModel.setUrl(url);
            feedSourceModel.setCategory(category);
            feedSourceModel.setName(name);
            sqLiteHelper.insertRecord(feedSourceModel);
            Toast toast = Toast.makeText(this, "Source added successfully",
                    Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Please enter name and url",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
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
}

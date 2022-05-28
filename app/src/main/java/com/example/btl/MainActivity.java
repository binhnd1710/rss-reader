package com.example.btl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;


import com.example.btl.activities.AddSourceActivity;
import com.example.btl.activities.DescriptionActivity;
import com.example.btl.fragments.FeedListFragment;
import com.example.btl.fragments.SourceListFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, FeedListFragment.Listener,
        SourceListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = new SourceListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_source) {
            Intent intent = new Intent(this, AddSourceActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.nav_business:
                bundle.putString("category", "Business");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_entertainment:
                bundle.putString("category", "Entertainment");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_sport:
                bundle.putString("category", "Sport");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_technology:
                bundle.putString("category", "Technology");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_other:
                bundle.putString("category", "Other");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
            default:
                bundle.putString("category", "");
                fragment = new SourceListFragment();
                fragment.setArguments(bundle);
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemClicked(long id) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra(DescriptionActivity.ID, (int) id);
        startActivity(intent);
    }

    @Override
    public void sourceClicked(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", (int) id);
        Fragment fragment = new FeedListFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
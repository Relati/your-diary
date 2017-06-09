package com.study.android.kehan.diary_test01;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listview;
    List<Map<String, Object>> list2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.list_diary);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,getData(),R.layout.diary_item,
                new String[]{"record_weather","record_time"},
                new int[]{R.id.record_weather,R.id.record_time});
        listview.setAdapter(simpleAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final AlertDialog.Builder memorandumBuilder = new AlertDialog.Builder(DiaryListActivity.this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(DiaryListActivity.this);
                final View view1 = factory.inflate(R.layout.layout_dialog,null);
                //对话框
                memorandumBuilder.setTitle("添加备忘录").setView(view1).setNegativeButton("取消",null).setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //添加备忘录
                    }
                }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(DiaryListActivity.this,EditActivity.class);
            startActivity(mIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Jauary) {
            // Handle the camera action
        } else if (id == R.id.February) {

        } else if (id == R.id.March) {

        } else if (id == R.id.April) {

        } else if (id == R.id.May) {

        } else if (id == R.id.June) {

        } else if (id == R.id.July) {

        } else if (id == R.id.August) {

        } else if (id == R.id.September) {

        } else if (id == R.id.October) {

        } else if (id == R.id.November) {

        } else if (id == R.id.December) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Map<String,Object>> getData() {

        Map<String, Object> map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月11日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月12日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月13日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月14日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月15日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月16日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月17日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月18日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月19日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月20日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月21日");
        list2.add(map);

        map = new HashMap<>();
        map.put("record_weather","晴");
        map.put("record_time","11月22日");
        list2.add(map);

        return list2;
    }
}

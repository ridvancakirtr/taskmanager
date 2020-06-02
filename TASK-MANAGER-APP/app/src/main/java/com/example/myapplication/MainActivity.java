package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.RetrofitAPI.ApiClient;
import com.example.myapplication.RetrofitAPI.TaskHolderAPI;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getName();
    static MainActivity mainActivity;
    TabLayout tabLayout;
    TabItem daily, weekly, monthly;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        daily = findViewById(R.id.daily);
        weekly = findViewById(R.id.weekly);
        monthly = findViewById(R.id.monthly);
        viewPager = findViewById(R.id.viewPager);
        mainActivity = this;
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            Toast.makeText(mainActivity, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllTask:
                deleteAllTask();
                return true;
            case R.id.exitApp:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public void deleteAllTask(){
        TaskHolderAPI taskHolderAPI = ApiClient.getClient().create(TaskHolderAPI.class);
        Call<TaskModel> call = taskHolderAPI.delAllTask();
        call.enqueue(new Callback<TaskModel>() {
            @Override
            public void onResponse(Call<TaskModel> call, Response<TaskModel> response) {
                Log.d(TAG, call.toString() + " ----- " + response.message());
                Log.d(TAG, call.toString() + " ----- " + response.body());

                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "HATA");
                    return;
                } else {
                    if (response.body() != null) {
                        Toast.makeText(MainActivity.this, "All Task Removed" , Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskModel> call, Throwable t) {
                Log.d(TAG, call.request() + " ----- " + t.getMessage());
            }


        });
    }
}
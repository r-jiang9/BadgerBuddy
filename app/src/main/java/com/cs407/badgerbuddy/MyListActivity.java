package com.cs407.badgerbuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cs407.badgerbuddy.R;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {
    private ArrayList<String> myList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ExpandableListView expandableListView;

    private BroadcastReceiver addItemReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.cs407.badgerbuddy.ADD_TO_MY_LIST".equals(action)) {
                String floorPlanName = intent.getStringExtra("FloorPlanName");
                addItemToList(floorPlanName);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        // Set up the expandable list view and adapter
        // ...

        // Register receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(addItemReceiver,
                new IntentFilter("com.cs407.badgerbuddy.ADD_TO_MY_LIST"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addItemReceiver);
    }

    private void addItemToList(String item) {
        // Add the item to your list data structure and update the UI
        myList.add(item);
        adapter.notifyDataSetChanged(); // If you're using an ArrayAdapter or similar
    }
}

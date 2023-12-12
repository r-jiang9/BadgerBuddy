package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayListActivity extends AppCompatActivity {

    TextView listName;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        listView = findViewById(R.id.listViewFloorPlans);

        listName = (TextView) findViewById(R.id.textViewListName);
        //get all info from list activity
        String name = getIntent().getStringExtra("name");
        ArrayList<String> elements = (ArrayList<String>) getIntent().getSerializableExtra("elements");
        listName.setText(name);
        ArrayList<String> titles = (ArrayList<String>) getIntent().getSerializableExtra("titles");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DisplayListActivity.this, BuildingFloorActivity.class);
                i.putExtra("address", elements.get(position));
                i.putExtra("title", titles.get(position));
                startActivity(i);
            }
        });
    }
}
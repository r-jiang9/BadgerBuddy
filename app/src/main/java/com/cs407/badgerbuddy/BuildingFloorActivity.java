package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BuildingFloorActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private BuildingFloorListAdapter buildingFloorListAdapter;

    private List<String> listDataHeader;
    private HashMap<String, Integer> listDataChild;

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_floor);

        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvAddress = findViewById(R.id.tv_address);

        tvTitle.setText(title);
        tvAddress.setText(address);

        ImageButton imageButton = findViewById(R.id.btn_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // reset data
        initData();

        // create adapter
        expandableListView = findViewById(R.id.expandableListView);
        buildingFloorListAdapter = new BuildingFloorListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(buildingFloorListAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // floorplans
        listDataHeader.add("Floor P");
        listDataHeader.add("Floor 1");
        listDataHeader.add("Floor 2");
        listDataHeader.add("Floor 3");
        listDataHeader.add("Floor 4");

        // image of floorplans
        listDataChild.put(listDataHeader.get(0), R.drawable.memu_basement);
        listDataChild.put(listDataHeader.get(1), R.drawable.memu_1);
        listDataChild.put(listDataHeader.get(2), R.drawable.memu_2);
        listDataChild.put(listDataHeader.get(3), R.drawable.memu_3);
        listDataChild.put(listDataHeader.get(4), R.drawable.memu_4);
    }
}
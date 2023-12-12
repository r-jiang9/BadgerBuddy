package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.cs407.badgerbuddy.databinding.ActivityListBinding;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
// This class will display all the lists the user has created
    ListAdapter listAdapter;
    EditText listName;
    ActivityListBinding binding;
    static ArrayList<DisplayListModel> listElements = new ArrayList<>();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listAdapter = new ListAdapter(ListActivity.this, listElements);
        binding.listViewMyLists.setAdapter(listAdapter);

        //when clicking on list item
        binding.listViewMyLists.setClickable(true);
        binding.listViewMyLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListActivity.this, DisplayListActivity.class);
                i.putExtra("name", listElements.get(position).listName);
                i.putExtra("elements", listElements.get(position).elements);
                i.putExtra("titles", listElements.get(position).titles);
                startActivity(i);
            }
        });

        //when clicking on button
        button = (Button) findViewById(R.id.buttonMakeList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listName = findViewById(R.id.editTextListName);
                String name = listName.getText().toString();
                if(name != null || name.equals("")){
                    //add to list
                    listElements.add(new DisplayListModel(name));
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    // call DisplayListActivity for the appropriate list whenever an item in the ListView is selected
}
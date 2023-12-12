package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateListActivity extends AppCompatActivity {
    EditText listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
    }

    public void onClickSave(View view){
        // User presses the save button -> the new list is created, they are taken back to the list of lists
        // add code to save the new list
        listName = findViewById(R.id.editTextListName);
        String name = listName.getText().toString();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("listName", name);
        startActivity(intent);
        finish();
    }

}
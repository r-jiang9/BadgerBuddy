package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
    }

    public void onClickSave(View view){
        // User presses the save button -> the new list is created, they are taken back to the list of lists
        // add code to save the new list
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void onClickBack(View view){
        // User presses the back button -> they get taken back to the list of lists
        // info entered in EditText will not be saved
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
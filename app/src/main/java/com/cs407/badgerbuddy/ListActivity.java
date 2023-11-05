package com.cs407.badgerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {
// This class will display all the lists the user has created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void onClickCreateList(View view){
        Intent intent = new Intent(this, CreateListActivity.class);
        startActivity(intent);
    }

    // call DisplayListActivity for the appropriate list whenever an item in the ListView is selected
}
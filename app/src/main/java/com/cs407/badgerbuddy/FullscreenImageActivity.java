package com.cs407.badgerbuddy;

import android.app.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FullscreenImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        // retrieve the image resource ID passed from the previous activity
        int imageResId = getIntent().getIntExtra("imageResId", 0);

        // set up ImageView
        ImageView imageView = findViewById(R.id.fullscreen_image);
        imageView.setImageResource(imageResId);

        // set up back button
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close activity
                finish();
            }
        });
    }
}

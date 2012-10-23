package com.edmodo.instandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private final static int CAMERA_CAPTURE = 1;
    
    private ImageView mPicView;
    private Bitmap mPicBitmap;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mPicView = (ImageView) findViewById(R.id.image_content);
        Button btnCamera = (Button) findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Successfully retrieved image from camera
        if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK) {
            mPicBitmap = (Bitmap) data.getExtras().get("data");
            mPicView.setImageBitmap(mPicBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

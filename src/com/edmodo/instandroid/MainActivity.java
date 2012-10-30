package com.edmodo.instandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.jabistudio.androidjhlabs.filter.SolarizeFilter;
import com.jabistudio.androidjhlabs.filter.util.AndroidUtils;

public class MainActivity extends Activity {

    private final static int CAMERA_CAPTURE = 1;
    
    private ImageView mPicView;
    private Bitmap mOrigBitmap;
    
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
        
        // Handles orientation change
        if (savedInstanceState != null) {
            mOrigBitmap = (Bitmap) savedInstanceState.getParcelable("m_pic");
            if (mOrigBitmap != null) 
                mPicView.setImageBitmap(mOrigBitmap);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("m_pic", mOrigBitmap);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Successfully retrieved image from camera
        if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK) {
            mOrigBitmap = (Bitmap) data.getExtras().get("data");
            
            // Filter image
            //InvertFilter invertFilter = new InvertFilter();
            SolarizeFilter solarFilter = new SolarizeFilter();
            int[] src = AndroidUtils.bitmapToIntArray(mOrigBitmap);
            int width = mOrigBitmap.getWidth();
            int height = mOrigBitmap.getHeight();
            //int[] dest = invertFilter.filter(src, width, height);
            int[] dest = solarFilter.filter(src, width, height);
            
            Bitmap destBitmap = Bitmap.createBitmap(dest, width, height, Config.ARGB_8888);
            mPicView.setImageBitmap(destBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

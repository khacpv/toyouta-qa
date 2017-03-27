package com.example.ngothi.feebbackquality;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class thuhoi_activity extends Activity {
    ImageView vt1;
    Button Quaylai,DongY;
    TextView tv;
    int  do_dai=0;
    int index=0;
    Bitmap originalBitmap;
    String[] tenFile = new String[200];
    float scr_x_start,scr_x_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuhoi_activity);
        Quaylai = (Button) findViewById(R.id.btnback_loi);
        DongY = (Button) findViewById(R.id.btnDongY);
        vt1 = (ImageView) findViewById(R.id.image2);

        File file = new File("/sdcard/DCIM/Camera");
        do_dai = file.list().length;
        for (int i=0;i<do_dai;i++)
            tenFile[i]=file.list()[i];
        originalBitmap = BitmapFactory.decodeFile("/sdcard/DCIM/Camera/"+tenFile[0]);
        vt1.setImageBitmap(originalBitmap);

        Quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuhoi_activity.super.finish();
            }
        });
        DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                Bundle ten_image = new Bundle();
                ten_image.putString("tenfile",tenFile[index]);
                data.putExtra("GoiTen", ten_image);
                thuhoi_activity.this.setResult(Activity.RESULT_OK, data);
                thuhoi_activity.super.finish();
            }
        });
        vt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        scr_x_end=event.getX();
                        //Toast.makeText(thuhoi_activity.this," "+scr_x_end,Toast.LENGTH_LONG).show();
                        if(scr_x_end<200)
                    {
                        index--;
                        if(index<0)
                            index=do_dai-1;
                        String s= "/sdcard/DCIM/Camera/"+tenFile[index];
                        originalBitmap = BitmapFactory.decodeFile(s);
                        vt1.setImageBitmap(originalBitmap);
                    }
                        if(scr_x_end>500)
                        {
                            index++;
                            if(index==do_dai)
                                index=0;
                            String s= "/sdcard/DCIM/Camera/"+tenFile[index];
                            originalBitmap = BitmapFactory.decodeFile(s);
                            vt1.setImageBitmap(originalBitmap);
                        }
                    break;
                }
                return false;
            }
        });
    }
}

package com.example.binbin.screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;
    Button share,preview,cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(getVideoThumbnail(Environment
                .getExternalStoragePublicDirectory(Environment
                        .DIRECTORY_DOWNLOADS) + "/video.mp4"));

        share = (Button)findViewById(R.id.share);
        preview = (Button)findViewById(R.id.preview);
        cancle = (Button)findViewById(R.id.cancle);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);

                shareIntent.setType("audio/*");

                File vedioFile = new File(Environment
                        .getExternalStoragePublicDirectory(Environment
                                .DIRECTORY_DOWNLOADS), "/video.mp4");

                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(vedioFile));

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));


            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);

                File vedioFile = new File(Environment
                        .getExternalStoragePublicDirectory(Environment
                                .DIRECTORY_DOWNLOADS), "/video.mp4");
                Uri uri = Uri.parse(vedioFile.getAbsolutePath());
                intent.setDataAndType(uri,"video/*");
                startActivity(intent);

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}

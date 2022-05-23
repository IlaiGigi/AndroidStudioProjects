package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class RetrieveBitmap extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;
    private final ProgressBar progressBar;
    private final TextView tvSaveToGallery;

    public RetrieveBitmap(ImageView iv, ProgressBar pb, TextView tv) {
        imageView = iv;
        progressBar = pb;
        tvSaveToGallery = tv;
    }

    protected Bitmap doInBackground(String... urls) {
        InputStream in = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        } catch (Exception e) {
            Log.d("RetrieveBitmap", e.getMessage());
        }
            return null;
    }

    protected void onPostExecute(Bitmap feed) {
        progressBar.setVisibility(View.GONE);
        imageView.setImageBitmap(feed);
        tvSaveToGallery.setVisibility(View.VISIBLE);
    }
}

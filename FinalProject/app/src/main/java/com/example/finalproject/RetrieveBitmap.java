package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class RetrieveBitmap extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;

    public RetrieveBitmap(ImageView iv) {
        imageView = iv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Add animation and set it in the imageview
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
        // Remove imageview (background = null)
        imageView.setImageBitmap(feed);
    }
}

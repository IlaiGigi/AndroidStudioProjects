package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class KidsBoard extends LinearLayout implements View.OnClickListener{

    private static final int[][] level1ResourceIndexes = {{1,3,8},{0,7,5},{6,4,2}};
    private static final int[][] level2ResourceIndexes = {{0,7,1},{1,3,8},{2,5,4}};
    private static final int[][] level3ResourceIndexes = {{7,2,5},{6,7,0},{1,3,4}};
    public static final int[][][] levelResourceIndexes = {level1ResourceIndexes, level2ResourceIndexes, level3ResourceIndexes};
    public static final int BOARD_WIDTH_PX = 1050; // = 400dp
    public static final int BOARD_HEIGHT_PX = 1050; // = 400dp
    public static final int ROWS_NUM = 3;
    public static final int COLS_NUM = 3;
    private KidsTile[][] tiles; // 3x3 2d array
    private LinearLayout[] rows;
    private final Size boardSize;
    private final DBHelper dbHelper;
    private final SharedPreferences sp;

    public KidsBoard(Context context){
        super(context);
        setOrientation(VERTICAL);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(BOARD_WIDTH_PX, BOARD_HEIGHT_PX);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(params);

        boardSize = new Size(ROWS_NUM, COLS_NUM);
        tiles = new KidsTile[ROWS_NUM][COLS_NUM];


        rows = new LinearLayout[]{new LinearLayout(context), new LinearLayout(context), new LinearLayout(context)};
        for (int i=0; i<ROWS_NUM; i++){
            rows[i].setOrientation(HORIZONTAL);
            rows[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BOARD_HEIGHT_PX / 3));
            addView(rows[i]);
        }

        dbHelper = new DBHelper(getContext(), null, null, 1);

        sp = Utils.defineSharedPreferences(context, "mainRoot");
    }

    // Getters
    public Size getBoardSize() {return boardSize;}
    public KidsTile[][] getTiles() {return tiles;}


    @Override
    public void onClick(View view) {
        // Construct dialog using the parameters of the tile
        KidsTile tile = (KidsTile) view;

        if (tile.isSolved())
            return;

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.kids_tile_dialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        ImageView ivPlayRecording = promptView.findViewById(R.id.ivPlayRecording);
        ImageView[] ivs = {promptView.findViewById(R.id.ivOpt1), promptView.findViewById(R.id.ivOpt2), promptView.findViewById(R.id.ivOpt3)};

        int num = new Random().nextInt(3); // Decide upon the correct option

        ivs[num].setColorFilter(getResources().getColor(KidsTile.colorResources[tile.getResourceIndex()]));

        int startFrom = 0;
        int[] invalidIndexes = new int[2];
        for (int i=0; i<3; i++){
            if (i != num){
                ivs[i].setColorFilter(getResources().getColor(KidsTile.colorResources[tile.getOptions().get(startFrom)]));
                invalidIndexes[startFrom] = i;
                startFrom++;
            }
        }

        ivPlayRecording.setOnClickListener(view1 -> {
            // Play the recording
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), KidsTile.audioResources[tile.getResourceIndex()]);
            mediaPlayer.start();
        });

        // Correct option
        ivs[num].setOnClickListener(view1 -> {
            // Play "correct" sound effect
            if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).isSound()){
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.coin_sound);
                mediaPlayer.start();
            }
            tile.setIsSolved(true);
            tile.setBackgroundColor(getResources().getColor(KidsTile.colorResources[tile.getResourceIndex()]));
            alertD.cancel();
            try {
                checkForCompletion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Incorrect options
        ivs[invalidIndexes[0]].setOnClickListener(view1 -> {
            // Start level 2
        });
        ivs[invalidIndexes[1]].setOnClickListener(view1 -> {
            // Start level 3
        });

        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertD.setView(promptView);
        alertD.setCancelable(true);
        alertD.show();
    }

    public void addTile(KidsTile tile){
        tiles[tile.getIndex().x][tile.getIndex().y] = tile;
    }

    public void loadTiles(){
        // Make sure there are no empty tile cells
        for (int i=0; i<ROWS_NUM; i++){
            for (int j=0; j<COLS_NUM; j++){
                if (tiles[i][j] == null)
                    return;
            }
        }

        for (int i=0; i<ROWS_NUM; i++){
            for (int j=0; j<COLS_NUM; j++){
                tiles[i][j].setOnClickListener(this);
                rows[i].addView(tiles[i][j]);
            }
        }
    }

    public void checkForCompletion() throws IOException {
        for (int i=0; i<ROWS_NUM; i++){
            for (int j=0; j<COLS_NUM; j++){
                if (!tiles[i][j].isSolved())
                    return;
            }
        }

        SharedPreferences sp = Utils.defineSharedPreferences(getContext(), "mainRoot");
        DBHelper dbHelper = new DBHelper(getContext(), null, null, 1);
        User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
        user.setCoins(user.getCoins() + 200);
        dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
        dbHelper.insertNewUser(user);

        if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).isSound()){
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.level_completed_sound_effect);
            mediaPlayer.start();
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.level_completed_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();

        ImageButton ibGoBackToLevelSelection = v.findViewById(R.id.ibGoBackToLevelSelection);
        ImageView ivLevelCompleted = v.findViewById(R.id.ivLevelCompleted);
        ProgressBar pbLevelCompleted = v.findViewById(R.id.pbLevelCompleted);
        TextView tvSaveToGallery = v.findViewById(R.id.tvSaveToGallery);

        RetrieveBitmap retrieveBitmap = new RetrieveBitmap(ivLevelCompleted, pbLevelCompleted, tvSaveToGallery);
        retrieveBitmap.execute("https://random.imagecdn.app/300/420");

        tvSaveToGallery.setOnClickListener(view -> {
            // Save the image to gallery
            Bitmap bitmap = ((BitmapDrawable) ivLevelCompleted.getDrawable()).getBitmap();
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), null);
            Toast.makeText(getContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
        });

        ibGoBackToLevelSelection.setOnClickListener(view2 -> {getContext().startActivity(new Intent(getContext(), MainActivity.class));});

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(v);
        dialog.setCancelable(true);
        dialog.show();
    }

}

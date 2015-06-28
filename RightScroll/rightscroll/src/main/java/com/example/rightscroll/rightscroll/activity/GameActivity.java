package com.example.rightscroll.rightscroll.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.rightscroll.rightscroll.R;
import com.example.rightscroll.rightscroll.core.Game;
import com.example.rightscroll.rightscroll.view.GameView;

import java.util.Observable;
import java.util.Observer;

public class GameActivity extends ActionBarActivity implements SurfaceHolder.Callback {

    private Game game;

    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ゲーム初期化
        initializeGame();
        // View初期化
        setContentView(R.layout.activity_game);
        view = (GameView) findViewById(R.id.game_view);
        view.setGame(game);
        view.addCallback(this);
    }

    private void initializeGame() {
        game = new Game(null);

        Point size = getDisplaySize();
        game.setWidth(size.x);
        game.setHeight(size.y);

        game.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                Game game = (Game) observable;
                if (game.getState() == Game.GameState.GAME_OVER) {
                    if (loadHighScore() < game.getScore()) {
                        saveHighScore(game.getScore());
                    }
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        game.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.start();
    }

    public void saveHighScore(int highScore) {
        SharedPreferences sharedPref = // getPreferences(Context.MODE_PRIVATE);
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score), highScore);
        editor.commit();
    }

    public int loadHighScore() {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getInt(getString(R.string.saved_high_score), 0);
    }

    private Point getDisplaySize() {
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (game.getState() == Game.GameState.RUNNING) {
            boolean elevates = false;
            boolean accelerates = false;

            for (int i = 0; i < event.getPointerCount(); i++) {
                if (event.getX(i) < game.getWidth() / 2) {
                    elevates = true;
                } else {
                    accelerates = true;
                }
            }
            if (elevates) {
                game.getMainCharacter().elevate();
            }
            if (accelerates) {
                game.getMainCharacter().accelerate();
            }
        } else if (game.getState() == Game.GameState.GAME_OVER) {
            // TODO 一度 event.getAction() == MotionEvent.ACTION_UP を確認してから
//            initializeGame();
//            game.start();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}

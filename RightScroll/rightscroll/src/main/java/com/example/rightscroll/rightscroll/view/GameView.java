package com.example.rightscroll.rightscroll.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.rightscroll.rightscroll.core.Game;
import com.example.rightscroll.rightscroll.core.object.GameObject;
import com.example.rightscroll.rightscroll.core.object.Obstacle;
import com.example.rightscroll.rightscroll.core.util.GameUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * ゲーム描画クラス。ゲームの状態を元に描画を行う。
 *
 * @author newuser
 *
 */
public class GameView extends SurfaceView implements Observer {

    private Game game;

    private Paint textPaint;

    private Paint objectPaint;

    private Paint mainCharacterPaint;

    private int mainCharacterColor = Color.rgb(200, 255, 200);

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

        // text用
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);

        // GameObject用
        objectPaint = new Paint();
        objectPaint.setAntiAlias(true);
        objectPaint.setColor(Color.rgb(180, 180, 180));

        mainCharacterPaint = new Paint();
        mainCharacterPaint.setAntiAlias(true);
        mainCharacterPaint.setColor(mainCharacterColor);
	}

    public void setGame(Game game) {
        if (this.game != null) {
            this.game.deleteObserver(this);
        }
        this.game = game;
        game.addObserver(this);
    }

    private void doDraw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.save();

        canvas.drawColor(Color.WHITE);

        // 敵にヒットしていたら赤くする
        if (game.getMainCharacter().isHit()) {
            mainCharacterPaint.setColor(Color.rgb(200, 80, 80));
        } else {
            mainCharacterPaint.setColor(mainCharacterColor);
        }

        // 味方と敵を描画
        drawGameObject(game.getMainCharacter(), canvas, mainCharacterPaint);
        for (Obstacle obstacle : game.getObstacles()) {
            drawGameObject(obstacle, canvas, objectPaint);
        }

        // ゲーム情報(スコア等)表示
        canvas.drawText("score : " + GameUtil.formatScore(game.getScore()), game.getWidth() - 100, 20, textPaint);
        canvas.drawText("damage : " + game.getDamage() , game.getWidth() - 100, 40, textPaint);

        // ゲームオーバー時
        if (game.getState() == Game.GameState.GAME_OVER) {
            Paint gameOverPaint = new Paint();
            gameOverPaint.setTextSize(game.getWidth() / 10);
            gameOverPaint.setColor(Color.BLACK);
            canvas.drawText("GAME OVER !!!" , game.getWidth() / 5, game.getHeight() / 2, gameOverPaint);
        }

        canvas.restore();
        getHolder().unlockCanvasAndPost(canvas);
    }

    public void addCallback(SurfaceHolder.Callback callback) {
        getHolder().addCallback(callback);
    }

    public void removeCallback(SurfaceHolder.Callback callback) {
        getHolder().removeCallback(callback);
    }

    private void drawGameObject(GameObject gameObj, Canvas canvas, Paint paint) {
        canvas.drawCircle(gameObj.getX() + gameObj.getWidth() / 2, gameObj.getY() + gameObj.getWidth() / 2, gameObj.getWidth(), paint);
    }

	@Override
	public void update(Observable observable, Object data) {
		doDraw();
	}

}

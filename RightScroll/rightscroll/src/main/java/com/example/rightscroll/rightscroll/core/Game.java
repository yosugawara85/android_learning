package com.example.rightscroll.rightscroll.core;

import com.example.rightscroll.rightscroll.core.object.MainCharacter;
import com.example.rightscroll.rightscroll.core.object.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ゲーム管理クラス
 *
 * <ol>
 * <li>ゲームの制御</li>
 * <li>状態変更の通知</li>
 * </ol>
 * 
 * @author newuser
 *
 */
public class Game extends Observable {

    private ScheduledExecutorService executor;

	private MainCharacter mainCharacter;

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();

    private List<Obstacle> garbageObstacles = new ArrayList<Obstacle>();

    private int score;

    private int damage;

    private int width;

    private int height;

    private Random random;

	private Stage stage;

    private GameState state = GameState.INIT;

    public enum GameState {
        INIT, RUNNING, GAME_OVER
    }

    public Game(Stage stage) {
        this.stage = stage;
        mainCharacter = new MainCharacter(GameConstants.MAIN_CHARACTER_INIT_POSITION_X, GameConstants.MAIN_CHARACTER_INIT_POSITION_Y,
                GameConstants.MAIN_CHARACTER_WIDTH,  GameConstants.MAIN_CHARACTER_WIDTH);

        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public void start() {
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleWithFixedDelay(new Runnable() {
            // executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (state == GameState.GAME_OVER) {
                    executor.shutdown();
                    return;
                }
                mainCharacter.setHit(false);
                // オブジェクト移動
                mainCharacter.move();
                for (Obstacle obstacle : obstacles) {
                    obstacle.move();
                    obstacle.move(-(int) mainCharacter.getVX(), 0);
                    // 画面から消えた障害物を保持
                    if (obstacle.getX() + obstacle.getWidth() < 0) {
                        garbageObstacles.add(obstacle);
                    }
                    mainCharacter.hit(obstacle);
                }
                // 画面から消えた障害物は削除
                obstacles.removeAll(garbageObstacles);
                garbageObstacles.clear();

                // ゲーム情報更新
                score += mainCharacter.getVX();
                if (mainCharacter.isHit()) {
                    damage++;
                    if (damage >= GameConstants.LIFE_POINT) {
                        state = GameState.GAME_OVER;
                    }
                }

                // デバッグ用
//                score = width * 1000 + height;
//                score = mainCharacter.getX() * 1000 + mainCharacter.getY();

                loadObstacles();
                setChanged();
                notifyObservers();
            }
        }, 1L, 25L, TimeUnit.MILLISECONDS);

        state = GameState.RUNNING;
    }

    private void loadObstacles() {
        // TODO 乱数で生成してるけど本当ならStageからロードする
        if (random.nextInt(99) < GameConstants.PERCENTAGE_OF_OBSTACLE_APPEARANCE) {
            Obstacle obstacle = new Obstacle(width, random.nextInt(height),  GameConstants.DEFAULT_OBSTACLE_WIDTH, GameConstants.DEFAULT_OBSTACLE_WIDTH);
            obstacle.setVX(-random.nextInt(4));
            obstacles.add(obstacle);
        }
    }

    public void stop() {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // TODO スルー？
        }
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getScore() {
        return score;
    }

    public int getDamage() {
        return damage;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public GameState getState() {
        return state;
    }
}
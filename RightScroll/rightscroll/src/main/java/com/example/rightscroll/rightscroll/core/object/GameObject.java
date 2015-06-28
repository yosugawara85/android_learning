package com.example.rightscroll.rightscroll.core.object;

import android.graphics.Rect;

import com.example.rightscroll.rightscroll.core.policy.IActionPolicy;


/**
 * ゲーム中で表示可能なオブジェクト。
 *
 * @author newuser
 *
 */
public class GameObject {

    private IActionPolicy policy;

	private Rect rect;

    private float vx;

    private float vy;

    public GameObject(int left, int top, int width, int height) {
        this.rect = new Rect(left, top, left + width, top + height);
    }
	
	public int getX() {
		return rect.left;
	}
	
	public int getY() {
		return rect.top;
	}

    public int getWidth() {
        return rect.width();
    }

    public int getHeight() {
        return rect.height();
    }

    public float getVX() {
        return vx;
    }

    public void setVX(float vx) {
        this.vx = vx;
    }

    public float getVY() {
        return vy;
    }

    public void setVY(float vy) {
        this.vy = vy;
    }

    public Rect getRect() {
        return rect;
    }

    public void setPolicy(IActionPolicy policy) {
        this.policy = policy;
    }

	public void move(int dx, int dy) {
		rect.left += dx;
        rect.right += dx;
        if (rect.top + dy < 0) {
            dy = -rect.top;
            setVY(0);
        } else if (rect.bottom + dy > 400) { // TODO heightを外部から取得したい
            dy = 400 - rect.bottom;
            setVY(0);
        }
        rect.top += dy;
        rect.bottom += dy;
    }

    public void move() {
        policy.move();
    }
}

package com.example.rightscroll.rightscroll.core.object;

import android.graphics.Rect;

import com.example.rightscroll.rightscroll.core.GameConstants;
import com.example.rightscroll.rightscroll.core.policy.DecelerateActionDecorator;
import com.example.rightscroll.rightscroll.core.policy.DefaultActionPolicy;
import com.example.rightscroll.rightscroll.core.policy.FallingActionDecorator;

/**
 * 主人公機
 */
public class MainCharacter extends GameObject {

    private static final int MIN_VY = -10;

    private static final int MAX_VX = 20;

    private boolean isHit = false;

    public MainCharacter(int left, int top, int width, int height) {
        super(left, top, width, height);
//        setPolicy(new FallingActionPolicy(this));
        setPolicy(new DecelerateActionDecorator(new FallingActionDecorator(new DefaultActionPolicy(this)),
                GameConstants.MAIN_CHARACTER_MIN_VX));
    }

    @Override
    public void move(int dx, int dy) {
        // 自機は横移動はしない
        super.move(0, dy);
    }

    public void hop() {
        setVY(MIN_VY);
    }

    public void elevate() {
        float vy = getVY() - 0.3f;
        if (vy < MIN_VY) {
            vy = MIN_VY;
        }
        setVY(vy);
    }

    public void accelerate() {
        float vx = getVX() + 0.3f;
        if (vx > MAX_VX) {
            vx = MAX_VX;
        }
        setVX(vx);
    }

    public boolean isHit() {
        return isHit;
    }

    public void hit(GameObject other) {
//        if (getRect().contains(other.getRect())) {
        if (Rect.intersects(getRect(), other.getRect())) {
            isHit = true;
        }
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }
}

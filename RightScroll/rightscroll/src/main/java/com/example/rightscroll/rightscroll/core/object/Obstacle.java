package com.example.rightscroll.rightscroll.core.object;

import com.example.rightscroll.rightscroll.core.policy.DefaultActionPolicy;

/**
 * 障害物
 */
public class Obstacle extends GameObject {

    public Obstacle(int left, int top, int width, int height) {
        super(left, top, width, height);
        setPolicy(new DefaultActionPolicy(this));
    }
}

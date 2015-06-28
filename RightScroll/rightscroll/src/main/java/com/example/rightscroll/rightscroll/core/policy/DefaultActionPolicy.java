package com.example.rightscroll.rightscroll.core.policy;

import com.example.rightscroll.rightscroll.core.object.GameObject;

/**
 * デフォルトの実装
 * 
 * @author newuser
 *
 */
public class DefaultActionPolicy implements IActionPolicy {

    protected GameObject gameObj;

    @Override
    public GameObject getGameObject() {
        return gameObj;
    }

    public DefaultActionPolicy(GameObject gameObj) {
        this.gameObj = gameObj;
    }

    @Override
    public void move() {
        gameObj.move((int)gameObj.getVX(), (int)gameObj.getVY());
    }
}

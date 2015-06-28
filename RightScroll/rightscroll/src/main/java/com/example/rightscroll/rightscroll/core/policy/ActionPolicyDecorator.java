package com.example.rightscroll.rightscroll.core.policy;

import com.example.rightscroll.rightscroll.core.object.GameObject;

/**
 * Created by newuser on 2015/05/06.
 */
public abstract class ActionPolicyDecorator implements IActionPolicy {
    private IActionPolicy policy;

    public ActionPolicyDecorator(IActionPolicy policy) {
        this.policy = policy;
    }

    public IActionPolicy getPolicy() {
        return policy;
    }

    public abstract void decorate();

    @Override
    public void move() {
        decorate();
        policy.move();
    }

    @Override
    public GameObject getGameObject() {
        return policy.getGameObject();
    }
}

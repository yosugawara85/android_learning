package com.example.rightscroll.rightscroll.core.policy;

/**
 * 自由落下
 *
 * @author newuser
 *
 */
public class FallingActionDecorator extends ActionPolicyDecorator {

    private static final int MAX_VY = 10;

    public FallingActionDecorator(IActionPolicy policy) {
        super(policy);
    }

    @Override
    public void decorate() {
        float vy = getPolicy().getGameObject().getVY() + 0.25f;
        if (vy > MAX_VY) {
            vy = MAX_VY;
        }
        getPolicy().getGameObject().setVY(vy);
    }
}

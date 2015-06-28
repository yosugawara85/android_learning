package com.example.rightscroll.rightscroll.core.policy;

/**
 * 減速
 *
 * @author newuser
 *
 */
public class DecelerateActionDecorator extends ActionPolicyDecorator {

    private int minVX;

    public DecelerateActionDecorator(IActionPolicy policy) {
        this(policy, 0);
    }

    public DecelerateActionDecorator(IActionPolicy policy, int minVX) {
        super(policy);
        this.minVX = minVX;
    }

    @Override
    public void decorate() {
        float vx = getPolicy().getGameObject().getVX() - 0.25f;
        if (vx < minVX) {
            vx = minVX;
        }
        getPolicy().getGameObject().setVX(vx);
    }
}

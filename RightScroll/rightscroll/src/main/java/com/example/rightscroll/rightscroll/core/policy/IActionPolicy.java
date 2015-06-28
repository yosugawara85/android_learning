package com.example.rightscroll.rightscroll.core.policy;

import com.example.rightscroll.rightscroll.core.object.GameObject;

public interface IActionPolicy {
	void move();

    GameObject getGameObject();
}

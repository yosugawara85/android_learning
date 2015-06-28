package com.example.rightscroll.rightscroll.core.util;

/**
 * Created by newuser on 2015/05/09.
 */
public final class GameUtil {

    public static String formatScore(int score) {
        StringBuilder sb = new StringBuilder();
        sb.append(score).insert(sb.length() - 2, ".").append("(m)");
        return sb.toString();
    }

    private GameUtil() {
    }
}

package com.icyfox.leetcoder.bean;

/**
 * Created by icyfox on 2015/3/26.
 */
public enum Difficulty {
    EASY, MEDIUM, HARD;

    public static Difficulty fromInteger(int x) {
        switch(x) {
            case 1:
                return EASY;
            case 2:
                return MEDIUM;
            default:
            case 3:
                return HARD;
        }
    }
}

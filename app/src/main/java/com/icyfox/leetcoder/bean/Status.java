package com.icyfox.leetcoder.bean;

/**
 * Created by icyfox on 2015/3/26.
 */
public enum Status {
    AC("AC"),
    NONE("NONE"),
    NOTAC("NOTAC");

    private String text;


    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Status fromString(String text) {
        if (text != null) {
            for (Status b : Status.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}

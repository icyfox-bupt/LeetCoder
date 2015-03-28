package com.icyfox.leetcoder.bean;

import android.text.TextUtils;

/**
 * Created by icyfox on 2015/3/26.
 */
public class Problem {

    private int id;
    private int acceptance;
    private Status status;
    private String url;
    private String title;
    private Diffculty diffculty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Diffculty getDiffculty() {
        return diffculty;
    }

    public void setDiffculty(Diffculty diffculty) {
        this.diffculty = diffculty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", acceptance=" + acceptance +
                ", status=" + status +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", diffculty=" + diffculty +
                '}';
    }

    public boolean empty(){
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url)) return true;
        return false;
    }

}

package com.icyfox.leetcoder.bean;

import android.text.TextUtils;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by icyfox on 2015/3/26.
 */
public class Problem extends SugarRecord<Problem> implements Serializable{

    private int pid;
    private int acceptance;
    private Status status;
    private String url;
    private String title;
    private Difficulty difficulty;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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
                "pid=" + pid +
                ", acceptance=" + acceptance +
                ", status=" + status +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }

    public boolean empty(){
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url)) return true;
        return false;
    }

}

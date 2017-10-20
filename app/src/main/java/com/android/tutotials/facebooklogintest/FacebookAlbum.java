package com.android.tutotials.facebooklogintest;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by oussamakaoui on 10/19/17.
 */

public class FacebookAlbum{

    private long id;
    private Date createdTime;
    private String name;

    public FacebookAlbum(long id, Date createdTime, String name) {
        this.id = id;
        this.createdTime = createdTime;
        this.name = name;
    }

    public FacebookAlbum() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FacebookAlbum{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", name='" + name + '\'' +
                '}';
    }
}

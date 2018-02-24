package com.example.phosphor.idecided;

/**
 * Created by Phosphor on 2/17/2018.
 */

public class commentsModel {

    String username, comment, timestamp;

    public commentsModel() {
    }

    public commentsModel(String username, String comment, String timestamp) {
        this.username = username;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

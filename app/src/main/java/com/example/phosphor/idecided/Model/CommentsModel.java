package com.example.phosphor.idecided.Model;

public class CommentsModel {

    @SuppressWarnings("WeakerAccess")
    public String username, comment, timestamp, converted_timestamp;

    public CommentsModel() {
    }

    public CommentsModel(String username, String comment, String timestamp) {
        this.username = username;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    @SuppressWarnings("WeakerAccess")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("WeakerAccess")
    public String getComment() {
        return comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @SuppressWarnings("WeakerAccess")
    public void setConverted_timestamp(String converted_timestamp) {
        this.converted_timestamp = converted_timestamp;
    }

    @SuppressWarnings("WeakerAccess")
    public String getConverted_timestamp() {
        return converted_timestamp;
    }
}

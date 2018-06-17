package com.teamtreehouse.giflib.web;

/**
 * Denis, 17.06.2018
 */
public class FlashMessage {
    public static enum Status {
        SUCCESS, FAILURE
    }

    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}

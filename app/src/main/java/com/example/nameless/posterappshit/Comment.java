package com.example.nameless.posterappshit;

/**
 * Created by Nameless on 5/9/2018.
 */

public class Comment {
    private String senderId, messageId, text;
    private long date;

    public Comment(String senderId, String messageId, String text, long date) {
        this.senderId = senderId;
        this.messageId = messageId;
        this.text = text;
        this.date = date;
    }

    public Comment() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (getDate() != comment.getDate()) return false;
        if (getSenderId() != null ? !getSenderId().equals(comment.getSenderId()) : comment.getSenderId() != null)
            return false;
        if (getMessageId() != null ? !getMessageId().equals(comment.getMessageId()) : comment.getMessageId() != null)
            return false;
        return getText() != null ? getText().equals(comment.getText()) : comment.getText() == null;
    }

    @Override
    public int hashCode() {
        int result = getSenderId() != null ? getSenderId().hashCode() : 0;
        result = 31 * result + (getMessageId() != null ? getMessageId().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (int) (getDate() ^ (getDate() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "senderId='" + senderId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}

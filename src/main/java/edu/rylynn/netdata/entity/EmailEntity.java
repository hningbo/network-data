package edu.rylynn.netdata.entity;

import java.util.Date;

public class EmailEntity {
    private String sender;
    private String reciever;
    private Date time;
    private String cotent;

    public EmailEntity(String sender, String reciever, Date time, String cotent) {
        this.sender = sender;
        this.reciever = reciever;
        this.time = time;
        this.cotent = cotent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sender: ").append(sender).append("\n").append("reciever: ").append(reciever).append("\n");
        return sb.toString();
    }
}

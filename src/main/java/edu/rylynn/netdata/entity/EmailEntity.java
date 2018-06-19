package edu.rylynn.netdata.entity;

import java.util.Date;

public class EmailEntity {
    private String from;
    private String to;
    private Date time;
    private String subject;
    private String content;

    public EmailEntity(String from, String to, Date time, String subject, String content) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.subject = subject;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sender: ").append(from).append("\n").append("reciever: ").append(to).append("\n").append("subject: ").append(subject).append("\n").append("content: ").append(content).append("\n");
        return sb.toString();
    }
}

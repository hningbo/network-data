package edu.rylynn.netdata.core.mail;

import edu.rylynn.netdata.entity.EmailEntity;
import org.pcap4j.core.PacketListener;

public abstract class ListenerMail implements PacketListener {
    protected String sender;
    protected String reciever;
    protected String content;

    ListenerMail(){
        this.sender = "";
        this.reciever = "";
        this.content = "";
    }
    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public String getContent() {
        return content;
    }
}

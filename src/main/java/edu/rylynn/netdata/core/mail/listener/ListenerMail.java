package edu.rylynn.netdata.core.mail.listener;

import edu.rylynn.netdata.entity.TCPTuple;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.TcpPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ListenerMail implements PacketListener {
    protected Map<TCPTuple, List<TcpPacket>> cache;

    public Map<TCPTuple, List<TcpPacket>> getCache() {
        return cache;
    }

    public ListenerMail(){
        this.cache = new HashMap<>();
    }
}

package edu.rylynn.netdata.core.mail.listener;

import edu.rylynn.netdata.entity.TCPTuple;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.TcpPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWebMailListener implements PacketListener {
    protected Map<TCPTuple, List<TcpPacket>> cache;

    public AbstractWebMailListener() {
        this.cache = new HashMap<>();
    }

    public Map<TCPTuple, List<TcpPacket>> getCache() {
        return cache;
    }

    public void clearCache() {
        this.cache.clear();
    }
}

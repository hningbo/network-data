package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.ListenerMail;
import edu.rylynn.netdata.entity.TCPTuple;
import org.pcap4j.packet.TcpPacket;

import java.util.List;
import java.util.Map;

public abstract class AbstractProcessor extends Thread {
    protected Map<TCPTuple, List<TcpPacket>> cache;

    public AbstractProcessor(ListenerMail listener) {
        this.cache = listener.getCache();
    }

    public void TCPRecover() {
        if (cache.size() != 0) {
            for (Map.Entry<TCPTuple, List<TcpPacket>> entry : cache.entrySet()) {
                List<TcpPacket> packets = entry.getValue();
                //TODO:recover the order of the tcp segment
                System.out.println(packets.size());
            }
        }
    }

    public abstract void sendMailExtract();

    public abstract void recieveMailExtract();

    @Override
    public void run() {
        while (true) {
            TCPRecover();
            sendMailExtract();
            recieveMailExtract();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

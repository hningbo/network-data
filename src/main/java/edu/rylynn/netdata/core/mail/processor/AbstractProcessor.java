package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;
import edu.rylynn.netdata.entity.TCPTuple;
import edu.rylynn.netdata.util.EnDeCoder;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.ByteArrays;

import java.util.*;

public abstract class AbstractProcessor extends Thread {
    protected Map<TCPTuple, List<TcpPacket>> cache;
    protected AbstractWebMailListener listener;

    public AbstractProcessor(AbstractWebMailListener listener) {
        this.listener = listener;
    }

    //tcp 重组以及还原http协议内容
    public void process() {
        if (cache.size() != 0) {
            for (Map.Entry<TCPTuple, List<TcpPacket>> entry : cache.entrySet()) {
                List<TcpPacket> packets = entry.getValue();
                List<TcpPacket> mailRequest = new ArrayList<>();
                TcpPacket metaPacket = packets.get(0);
                StringBuilder tempHttpContent = new StringBuilder();

                long firstAck = metaPacket.getHeader().getAcknowledgmentNumberAsLong();
                for (TcpPacket packet : packets) {
                    if (packet.equals(metaPacket)) {
                        mailRequest.add(metaPacket);
                        continue;
                    }
                    if (packet.getHeader().getAcknowledgmentNumberAsLong() == firstAck) {
                        mailRequest.add(packet);
                    }
                }

                Collections.sort(mailRequest, new Comparator<TcpPacket>() {
                    @Override
                    public int compare(TcpPacket t1, TcpPacket t2) {
                        return t1.getHeader().getSequenceNumber() - t2.getHeader().getSequenceNumber();
                    }
                });

                for (TcpPacket tcpPacket : mailRequest) {
                    byte[] thisRawData = tcpPacket.getPayload().getRawData();
                    String thisHexString = ByteArrays.toHexString(thisRawData, "");
                    tempHttpContent.append(EnDeCoder.hexStringToString(thisHexString));
                }
                String httpContent = tempHttpContent.toString();
                //System.out.println(httpContent);
                sendMailExtract(httpContent);
            }
        }

    }

    public abstract void sendMailExtract(String content);

    public abstract void recieveMailExtract(String content);

    @Override
    public void run() {
        while (true) {
            this.cache = listener.getCache();
            process();
            listener.clearCache();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

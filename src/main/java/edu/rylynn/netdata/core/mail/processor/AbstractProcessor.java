package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;
import edu.rylynn.netdata.entity.TCPTuple;
import edu.rylynn.netdata.util.EnDeCoder;
import org.pcap4j.packet.Packet;
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
                List<TcpPacket> mailResponse = new ArrayList<>();
                TcpPacket metaRequestPacket = packets.get(0);

                int srcPort = metaRequestPacket.getHeader().getSrcPort().valueAsInt();
                int dstPort = metaRequestPacket.getHeader().getDstPort().valueAsInt();

                StringBuilder tempHttpRequestContent = new StringBuilder();
                StringBuilder tempHttpResponseContent = new StringBuilder();

                long firstAck = metaRequestPacket.getHeader().getAcknowledgmentNumberAsLong();
                for (TcpPacket packet : packets) {
                    if (packet.equals(metaRequestPacket)) {
                        mailRequest.add(metaRequestPacket);
                        continue;
                    }
                    if (packet.getHeader().getAcknowledgmentNumberAsLong() == firstAck) {
                        mailRequest.add(packet);
                    }
                    if (packet.getHeader().getSrcPort().valueAsInt() == dstPort && packet.getHeader().getDstPort().valueAsInt() == srcPort) {
                        mailResponse.add(packet);
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
                    tempHttpRequestContent.append(EnDeCoder.hexStringToString(thisHexString));
                }

                String httpRequestContent = tempHttpRequestContent.toString();
                sendMailExtract(httpRequestContent);

                if (!mailResponse.isEmpty()) {
                    Collections.sort(mailResponse, new Comparator<TcpPacket>() {
                        @Override
                        public int compare(TcpPacket t1, TcpPacket t2) {
                            return t1.getHeader().getSequenceNumber() - t2.getHeader().getSequenceNumber();
                        }
                    });
                    for (TcpPacket tcpPacket : mailResponse) {
                        if (tcpPacket.getPayload() != null) {

                            byte[] thisRawData = tcpPacket.getPayload().getRawData();
                            String thisHexString = ByteArrays.toHexString(thisRawData, "");
                            tempHttpResponseContent.append(EnDeCoder.hexStringToString(thisHexString));
                        }
                    }
                    String httpResponseContent = tempHttpResponseContent.toString();

                    //System.out.println(httpContent);
                    recieveMailExtract(httpResponseContent);
                }

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
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

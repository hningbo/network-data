package edu.rylynn.netdata.core.mail.processor;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;
import edu.rylynn.netdata.dao.MongoHandler;
import edu.rylynn.netdata.entity.TCPTuple;
import edu.rylynn.netdata.util.EnDeCoder;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.ByteArrays;

import java.net.URLDecoder;
import java.util.*;

public abstract class AbstractProcessor extends Thread {
    protected Map<TCPTuple, List<TcpPacket>> cache;
    protected AbstractWebMailListener listener;
    protected MongoHandler handler;


    public AbstractProcessor(AbstractWebMailListener listener) {
        this.listener = listener;
        //handler = new MongoHandler("127.0.0.1", 27017);
    }

    //tcp 重组以及还原http协议内容
    public void process() {
        if (cache.size() != 0) {
            for (Map.Entry<TCPTuple, List<TcpPacket>> entry : cache.entrySet()) {
                List<TcpPacket> packets = entry.getValue();


                TcpPacket metaRequestPacket = packets.get(0);

                int srcPort = metaRequestPacket.getHeader().getSrcPort().valueAsInt();
                int dstPort = metaRequestPacket.getHeader().getDstPort().valueAsInt();

                StringBuilder tempHttpRequestContent = new StringBuilder();

                Collections.sort(packets, new Comparator<TcpPacket>() {
                    @Override
                    public int compare(TcpPacket t1, TcpPacket t2) {
                        return t1.getHeader().getSequenceNumber() - t2.getHeader().getSequenceNumber();
                    }
                });

;

                for (TcpPacket tcpPacket : packets) {
                    byte[] thisRawData;
                    try {
                        thisRawData = tcpPacket.getPayload().getRawData();
                    }catch (NullPointerException e){
                        continue;
                    }
                    String thisHexString = ByteArrays.toHexString(thisRawData, "");
                    tempHttpRequestContent.append(EnDeCoder.hexStringToString(thisHexString)).append("\n");
                }

                String httpRequestContent = tempHttpRequestContent.toString();
                sendMailExtract(httpRequestContent);
            }
        }
    }

    public abstract void sendMailExtract(String content);

    @Override
    public void run() {
        while (true) {
            this.cache = listener.getCache();
            try {
                process();
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.clearCache();
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package edu.rylynn.netdata.core.mail.listener;

import edu.rylynn.netdata.entity.TCPTuple;
import edu.rylynn.netdata.util.EnDeCoder;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Listener21CN extends ListenerMail {

    @Override
    public void gotPacket(Packet packet) {
        TcpPacket tcpPacket;
        IpV4Packet ipV4Packet;
        boolean mailSendMatch;
        boolean mailRecieveMatch;
        Pattern mailSendPattern = Pattern.compile(".*POST.*sendMail.do.*");
        Pattern mailRecievePattern = Pattern.compile("");
        try {
            ipV4Packet = IpV4Packet.newPacket(packet.getPayload().getRawData(), 0, packet.getPayload().length());
            tcpPacket = TcpPacket.newPacket(packet.getPayload().getPayload().getRawData(), 0, packet.getPayload().getPayload().length());
            if (tcpPacket.getHeader().getDstPort().valueAsInt() == 80) {
                String httpContent = EnDeCoder.hexStringToString(tcpPacket.toHexString());
                mailSendMatch = mailSendPattern.matcher(httpContent).find();
                TCPTuple tuple = new TCPTuple(ipV4Packet.getHeader().getSrcAddr(), ipV4Packet.getHeader().getDstAddr(), tcpPacket.getHeader().getSrcPort().valueAsInt(), tcpPacket.getHeader().getDstPort().valueAsInt());
                System.out.println(tuple);
                if (mailSendMatch) {
                    List<TcpPacket> packetList = new ArrayList<>();
                    packetList.add(tcpPacket);
                    cache.put(tuple, packetList);
                    return;
                }
                mailRecieveMatch = mailRecievePattern.matcher(httpContent).find();
                if (mailRecieveMatch) {
                    List<TcpPacket> packetList = new ArrayList<>();
                    packetList.add(tcpPacket);
                    cache.put(tuple, packetList);
                    return;
                }
                if (cache.containsKey(tuple)) {

                    List<TcpPacket> packetList = cache.get(tuple);
                    packetList.add(tcpPacket);
                }
            }
        } catch (IllegalRawDataException e) {
            e.printStackTrace();
        }
    }

}

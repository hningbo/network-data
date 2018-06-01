package edu.rylynn.netdata.core.mail;

import edu.rylynn.netdata.util.EnDeCoder;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;


public class Listener21CN extends ListenerMail {
    @Override
    public void gotPacket(Packet packet) {
//        try {
//            TcpPacket tcpPacket = TcpPacket.newPacket(packet.getRawData(), 10, 21);
//        } catch (IllegalRawDataException e) {
//            e.printStackTrace();
//        }
        TcpPacket newPacket = null;
        try {
            newPacket = TcpPacket.newPacket(packet.getPayload().getPayload().getRawData(), 0, packet.getPayload().getPayload().length());
            if(newPacket.getHeader().getDstPort().valueAsInt() == 80)
            {
                String line = EnDeCoder.hexStringToString(newPacket.toHexString());
            }
        } catch (IllegalRawDataException e) {
            e.printStackTrace();
        }
    }

}

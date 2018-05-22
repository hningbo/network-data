package edu.rylynn;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnknownHostException, PcapNativeException, NotOpenException, InterruptedException {
        List<PcapNetworkInterface> alldev = Pcaps.findAllDevs();
        System.out.println(alldev);
        PcapNetworkInterface nif = Pcaps.getDevByName("en0");
        int snaplen = 64 * 1024;
        int timeout = 50;
        PcapHandle.Builder phb = new PcapHandle.Builder(nif.getName()).snaplen(snaplen)
                .promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS).timeoutMillis(timeout)
                .bufferSize(1 * 1024 * 1024);
        PcapHandle handle = phb.build();
        Packet packet = handle.getNextPacket();

        int COUNT = 0;
        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                System.out.println(packet.getHeader());
            }
        };
        handle.loop(COUNT, listener);
    }
}

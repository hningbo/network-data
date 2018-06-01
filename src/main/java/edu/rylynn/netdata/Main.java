package edu.rylynn.netdata;

import edu.rylynn.netdata.core.mail.Listener21CN;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException, PcapNativeException, NotOpenException, InterruptedException {
//        List<PcapNetworkInterface> alldev = Pcaps.findAllDevs();
//        System.out.println(alldev);
//        PcapNetworkInterface nif = Pcaps.getDevByName("en0");
//        int snaplen = 64 * 1024;
//        int timeout = 50;

//        PcapHandle.Builder phb = new PcapHandle.Builder(nif.getName()).snaplen(snaplen)
//                .promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS).timeoutMillis(timeout)
//                .bufferSize(1 * 1024 * 1024);
//        PcapHandle handle = phb.build();
        PcapHandle handle = Pcaps.openOffline("./send_mail.gz.pcap");
        Packet packet = handle.getNextPacket();

        int COUNT = 0;
        PacketListener listener = new Listener21CN();

        handle.setFilter("tcp", BpfProgram.BpfCompileMode.OPTIMIZE);

        handle.loop(COUNT, listener);
    }
}

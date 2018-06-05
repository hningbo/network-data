package edu.rylynn.netdata;

import edu.rylynn.netdata.core.mail.listener.Listener21CN;
import edu.rylynn.netdata.core.mail.listener.ListenerMail;
import edu.rylynn.netdata.core.mail.processor.Process21CN;
import org.pcap4j.core.*;

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

        PcapHandle handle = null;
        try {
            handle = Pcaps.openOffline("./send_mail.gz.pcap");
            int COUNT = 0;
            ListenerMail listener = new Listener21CN();

            handle.setFilter("tcp", BpfProgram.BpfCompileMode.OPTIMIZE);
            new Process21CN(listener).start();
            handle.loop(COUNT, listener);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.ListenerMail;
import edu.rylynn.netdata.entity.TCPTuple;
import org.pcap4j.packet.TcpPacket;

import java.util.List;
import java.util.Map;

public class Process21CN extends AbstractProcessor{

    public Process21CN(ListenerMail listenerMail) {
        super(listenerMail);
    }

    @Override
    public void sendMailExtract() {

    }

    @Override
    public void recieveMailExtract() {

    }
}

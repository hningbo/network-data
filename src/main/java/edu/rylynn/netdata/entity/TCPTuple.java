package edu.rylynn.netdata.entity;

import java.io.Serializable;
import java.net.Inet4Address;

public class TCPTuple implements Serializable {
    private Inet4Address srcAddress;
    private Inet4Address dstAddress;
    private int srcPort;
    private int dstPort;

    public TCPTuple(Inet4Address srcAddress, Inet4Address dstAddress, int srcPort, int dstPort) {
        this.srcAddress = srcAddress;
        this.dstAddress = dstAddress;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
    }

    public Inet4Address getSrcAddress() {
        return srcAddress;
    }

    public Inet4Address getDstAddress() {
        return dstAddress;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    @Override
    public int hashCode() {
        return srcAddress.getHostAddress().hashCode() + srcPort + dstAddress.getHostAddress().hashCode() + dstPort;
    }

    @Override
    public boolean equals(Object obj) {
        TCPTuple anotherTuple = (TCPTuple)obj;
        return (srcAddress.getHostAddress().equals(anotherTuple.srcAddress.getHostAddress()) &&
                        dstAddress.getHostAddress().equals(anotherTuple.dstAddress.getHostAddress()) &&
                        srcPort == anotherTuple.srcPort && dstPort == anotherTuple.dstPort);
    }

    @Override
    public String toString() {
        return "src: " + srcAddress.getHostAddress() + ":" + srcPort + " dst: " + dstAddress + ":" + dstPort + " " + this.hashCode();
    }
}

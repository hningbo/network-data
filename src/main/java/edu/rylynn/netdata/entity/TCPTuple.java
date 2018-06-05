package edu.rylynn.netdata.entity;

import java.net.Inet4Address;

public class TCPTuple {
    private Inet4Address srcAddress;
    private Inet4Address dstAddress;
    private int srcPort;
    private int dstPort;
    private int hash;

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
        this.hash = srcAddress.getHostAddress().hashCode() + srcPort + dstAddress.getHostAddress().hashCode() + dstPort;
        return this.hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("src: ").append(srcAddress.getHostAddress()).append(" ").append(srcPort).append(" dst: ").append(dstAddress).append(" ").append(dstPort).append(" ").append(this.hashCode());
        return sb.toString();
    }
}

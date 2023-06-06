package top.javap.hermes.remoting.transport;

import top.javap.hermes.constant.CommConstant;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class TransporterConfig {

    private String className = CommConstant.DEFAULT_TRANSPORTER;
    private int acceptThreads = CommConstant.DEFAULT_ACCEPT_THREADS;
    private int ioThreads = CommConstant.DEFAULT_IO_THREADS;

    private boolean tcpNoDelay = true;
    private int tcpSendBuffer;
    private int tcpRecvBuffer;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAcceptThreads() {
        return acceptThreads;
    }

    public void setAcceptThreads(int acceptThreads) {
        this.acceptThreads = acceptThreads;
    }

    public int getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(int ioThreads) {
        this.ioThreads = ioThreads;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getTcpSendBuffer() {
        return tcpSendBuffer;
    }

    public void setTcpSendBuffer(int tcpSendBuffer) {
        this.tcpSendBuffer = tcpSendBuffer;
    }

    public int getTcpRecvBuffer() {
        return tcpRecvBuffer;
    }

    public void setTcpRecvBuffer(int tcpRecvBuffer) {
        this.tcpRecvBuffer = tcpRecvBuffer;
    }
}
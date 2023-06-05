package top.javap.hermes.remoting.message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * base 4byte
 * flags 1byte
 * requestId 4byte
 * bodyLength 4byte
 *
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public class Request extends BaseMessage {
    private static final AtomicInteger REQUEST_ID = new AtomicInteger(0);

    private boolean oneWay;// 1bit
    private int eventType;// 3bit
    private int requestId;
    private int bodyLength;
    private Object body;

    public Request() {
        setRequestId(nextId());
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    protected int nextId() {
        return REQUEST_ID.incrementAndGet();
    }
}
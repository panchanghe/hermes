package top.javap.hermes.remoting.message;

import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.remoting.message.pool.MessagePoolUtil;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public class Response extends BaseMessage {

    private int requestId;
    private byte status;
    private int bodyLength;
    private Object body;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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

    public static Response ok(int requestId, Object body) {
        Response response = MessagePoolUtil.getResponse();
        response.setRequestId(requestId);
        response.setStatus(MessageConstant.RES_STATUS_OK);
        response.setBody(body);
        return response;
    }

    public static Response failed(int requestId, Throwable t) {
        Response response = MessagePoolUtil.getResponse();
        response.setRequestId(requestId);
        response.setStatus(MessageConstant.RES_STATUS_FAILED);
        response.setBody(t.getMessage());
        return response;
    }
}
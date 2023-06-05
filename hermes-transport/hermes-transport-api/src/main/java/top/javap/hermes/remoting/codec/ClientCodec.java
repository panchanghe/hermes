package top.javap.hermes.remoting.codec;

import io.netty.buffer.ByteBuf;
import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.remoting.message.Request;
import top.javap.hermes.remoting.message.Response;
import top.javap.hermes.remoting.message.pool.MessagePoolUtil;
import top.javap.hermes.serialize.ObjectOutput;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public class ClientCodec extends TransportCodec<Request> {

    @Override
    protected Object decodeMessage(ByteBuf in) {
        short i = in.readShort();// magic
        Response response = MessagePoolUtil.getResponse();
        response.setVersion(in.readShort());
        response.setRequestId(in.readInt());
        response.setStatus(in.readByte());
        response.setBodyLength(in.readInt());
        response.setBody(decodeBody(response, in));
        return response;
    }

    private Object decodeBody(Response response, ByteBuf in) {
        return deserializer(in, response.getBodyLength()).readObject();
    }

    @Override
    protected void encodeMessage(Request request, ByteBuf out) {
        out.writeShort(request.getMagic());
        out.writeShort(request.getVersion());
        byte flags = 0x00;
        if (request.isOneWay()) {
            flags |= MessageConstant.FLAG_ONE_WAY;
        }
        flags |= request.getEventType();
        out.writeByte(flags);
        out.writeInt(request.getRequestId());
        out.writeInt(0);// bodyLength 稍后重写
        Object body = request.getBody();
        if (body instanceof Invocation) {
            encodeInvocation(((Invocation) body), out);
        }
        out.setInt(MessageConstant.REQ_BODY_LENGTH_OFFSET, out.writerIndex() - MessageConstant.REQ_HEADER_LENGTH);
        MessagePoolUtil.release(request);
    }

    private void encodeInvocation(Invocation invocation, ByteBuf out) {
        out.writeInt(invocation.key());
        ObjectOutput objectOutput = serializer(out);
        objectOutput.writeObject(invocation.arguments());
        objectOutput.writeObject(invocation.attachments());
    }

    @Override
    protected int headerLength() {
        return MessageConstant.RES_HEADER_LENGTH;
    }

    @Override
    protected int bodyLengthOffset() {
        return MessageConstant.RES_BODY_LENGTH_OFFSET;
    }
}
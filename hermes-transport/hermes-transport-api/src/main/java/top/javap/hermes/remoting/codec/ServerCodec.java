package top.javap.hermes.remoting.codec;

import io.netty.buffer.ByteBuf;
import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.remoting.message.Request;
import top.javap.hermes.remoting.message.Response;
import top.javap.hermes.remoting.message.RpcInvocation;
import top.javap.hermes.remoting.message.pool.MessagePoolUtil;
import top.javap.hermes.serialize.ObjectInput;

import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public class ServerCodec extends TransportCodec<Response> {

    @Override
    protected Object decodeMessage(ByteBuf in) {
        in.readShort();// magic 丢弃
        Request request = MessagePoolUtil.getRequest();
        request.setVersion(in.readShort());
        final byte flags = in.readByte();
        request.setOneWay((flags & MessageConstant.FLAG_ONE_WAY) != 0);
        request.setEventType(flags & MessageConstant.FLAG_EVENT_MASK);
        request.setRequestId(in.readInt());
        request.setBodyLength(in.readInt());
        request.setBody(decodeBody(request, in));
        return request;
    }

    private Object decodeBody(Request request, ByteBuf in) {
        if (request.getEventType() == MessageConstant.FLAG_EVENT_BIZ) {
            RpcInvocation invocation = new RpcInvocation();
            invocation.setKey(in.readInt());
            ObjectInput objectInput = deserializer(in, request.getBodyLength() - 4);
            invocation.setArguments((Object[]) objectInput.readObject());
            invocation.setAttachments((Map<String, Object>) objectInput.readObject());
            return invocation;
        }
        return null;
    }

    @Override
    protected void encodeMessage(Response response, ByteBuf out) {
        out.writeShort(response.getMagic());
        out.writeShort(response.getVersion());
        out.writeInt(response.getRequestId());
        out.writeByte(response.getStatus());
        out.writeInt(0);// bodyLength 稍后重写
        serializer(out).writeObject(response.getBody());
        out.setInt(MessageConstant.RES_BODY_LENGTH_OFFSET, out.writerIndex() - MessageConstant.RES_HEADER_LENGTH);
        MessagePoolUtil.release(response);
    }

    @Override
    protected int headerLength() {
        return MessageConstant.REQ_HEADER_LENGTH;
    }

    @Override
    protected int bodyLengthOffset() {
        return MessageConstant.REQ_BODY_LENGTH_OFFSET;
    }
}
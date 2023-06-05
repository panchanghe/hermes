package top.javap.hermes.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import top.javap.hermes.constant.CommConstant;
import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.serialize.ObjectInput;
import top.javap.hermes.serialize.ObjectOutput;
import top.javap.hermes.serialize.Serialization;
import top.javap.hermes.serialize.SerializationFactory;
import top.javap.hermes.util.Assert;

import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public abstract class TransportCodec<I> extends ByteToMessageCodec<I> {
    protected final Serialization serialization;

    public TransportCodec() {
        this(SerializationFactory.getSerialization(CommConstant.DEFAULT_SERIALIZATION));
    }

    protected TransportCodec(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, I message, ByteBuf out) throws Exception {
        encodeMessage(message, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> messages) throws Exception {
        while (true) {
            if (in.isReadable(headerLength())) {
                checkMagic(in);
                final int bodyLength = in.getInt(bodyLengthOffset());
                checkPayload(bodyLength);
                final int fullMessageLength = headerLength() + bodyLength;
                if (in.isReadable(fullMessageLength)) {
                    messages.add(decodeMessage(in));
                    continue;
                }
            }
            break;
        }
    }

    private void checkPayload(int size) {
        if (size > MessageConstant.MAX_PAYLOAD) {
            throw new RuntimeException("message exceed max payload:" + size);
        }
    }

    private void checkMagic(ByteBuf in) {
        final short magic = in.getShort(0);
        Assert.equals(MessageConstant.MAGIC, magic, "invalid magic");
    }

    protected ObjectOutput serializer(ByteBuf buf) {
        return serialization.serializer(new ByteBufOutputStream(buf));
    }

    protected ObjectInput deserializer(ByteBuf buf, int length) {
        return serialization.deserializer(new ByteBufInputStream(buf, length));
    }

    protected abstract Object decodeMessage(ByteBuf in);

    protected abstract void encodeMessage(I message, ByteBuf out);

    protected abstract int headerLength();

    protected abstract int bodyLengthOffset();
}
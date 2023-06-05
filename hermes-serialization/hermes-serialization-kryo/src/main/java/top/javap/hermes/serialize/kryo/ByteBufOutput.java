package top.javap.hermes.serialize.kryo;

import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;

/**
 * TODO 直接序列化写入到ByteBuf
 *
 * @author: pch
 * @description:
 * @date: 2023/6/5
 **/
public class ByteBufOutput extends Output {

    private final ByteBuf buf;

    public ByteBufOutput(ByteBuf buf) {
        this.buf = buf;
    }
}
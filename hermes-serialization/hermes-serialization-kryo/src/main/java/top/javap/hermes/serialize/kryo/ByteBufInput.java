package top.javap.hermes.serialize.kryo;

import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;

/**
 * TODO 直接从ByteBuf读数据反序列化
 *
 * @author: pch
 * @description:
 * @date: 2023/6/5
 **/
public class ByteBufInput extends Input {

    private final ByteBuf buf;

    public ByteBufInput(ByteBuf buf) {
        this.buf = buf;
    }
}
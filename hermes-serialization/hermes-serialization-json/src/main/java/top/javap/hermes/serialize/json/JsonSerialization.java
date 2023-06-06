package top.javap.hermes.serialize.json;

import top.javap.hermes.serialize.ObjectInput;
import top.javap.hermes.serialize.ObjectOutput;
import top.javap.hermes.serialize.Serialization;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class JsonSerialization implements Serialization {

    @Override
    public ObjectOutput serializer(OutputStream outputStream) {
        return new JsonObjectOutput(outputStream);
    }

    @Override
    public ObjectInput deserializer(InputStream inputStream) {
        return new JsonObjectInput(inputStream);
    }
}
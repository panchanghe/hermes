package top.javap.hermes.serialize;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public interface Serialization {

    ObjectOutput serializer(OutputStream outputStream);

    ObjectInput deserializer(InputStream inputStream);
}
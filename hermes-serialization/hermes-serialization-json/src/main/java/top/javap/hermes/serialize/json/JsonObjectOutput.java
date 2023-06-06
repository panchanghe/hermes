package top.javap.hermes.serialize.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import top.javap.hermes.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class JsonObjectOutput implements ObjectOutput {

    private final PrintWriter writer;

    public JsonObjectOutput(OutputStream outputStream) {
        writer = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    public void writeObject(Object obj) {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.write(obj);
        try {
            out.writeTo(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
        writer.println();
        writer.flush();
    }
}
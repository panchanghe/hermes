package top.javap.hermes.serialize.json;

import com.alibaba.fastjson.JSON;
import top.javap.hermes.serialize.ObjectInput;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class JsonObjectInput implements ObjectInput {

    private final BufferedReader reader;

    public JsonObjectInput(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public Object readObject() {
        String json = readLine();
        return JSON.parse(json);
    }

    private String readLine() {
        try {
            String line = reader.readLine();
            if (line == null || line.trim().length() == 0) {
                throw new EOFException();
            }
            return line;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
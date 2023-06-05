package top.javap.hermes.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import top.javap.hermes.serialize.ObjectInput;
import top.javap.hermes.serialize.ObjectOutput;
import top.javap.hermes.serialize.Serialization;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/26
 **/
public class KryoSerialization implements Serialization {

    @Override
    public ObjectOutput serializer(OutputStream outputStream) {
        return new KryoObjectOutput(outputStream);
    }

    @Override
    public ObjectInput deserializer(InputStream inputStream) {
        return new KryoObjectInput(inputStream);
    }

    private Kryo getKryo() {
        return KryoHolder.get();
    }

    private class KryoObjectOutput implements ObjectOutput {

        private final Output output;

        private KryoObjectOutput(OutputStream outputStream) {
            this.output = new Output(outputStream);
        }

        @Override
        public void writeObject(Object obj) {
            getKryo().writeClassAndObject(output, obj);
            output.flush();
        }
    }

    private class KryoObjectInput implements ObjectInput {

        private final Input input;

        private KryoObjectInput(InputStream inputStream) {
            this.input = new Input(inputStream);
        }

        @Override
        public Object readObject() {
            return getKryo().readClassAndObject(input);
        }
    }
}
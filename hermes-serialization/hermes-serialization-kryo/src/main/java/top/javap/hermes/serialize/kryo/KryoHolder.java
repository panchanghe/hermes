package top.javap.hermes.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import de.javakaffee.kryoserializers.*;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class KryoHolder {

    private static final ThreadLocal<Kryo> ectype = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            Kryo kryo = new CompatibleKryo();
            kryo.setRegistrationRequired(false);
            kryo.addDefaultSerializer(Throwable.class, new JavaSerializer());
            kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
            kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
            kryo.register(InvocationHandler.class, new JdkProxySerializer());
            kryo.register(BigDecimal.class, new DefaultSerializers.BigDecimalSerializer());
            kryo.register(BigInteger.class, new DefaultSerializers.BigIntegerSerializer());
            kryo.register(Pattern.class, new RegexSerializer());
            kryo.register(BitSet.class, new BitSetSerializer());
            kryo.register(URI.class, new URISerializer());
            kryo.register(UUID.class, new UUIDSerializer());
            UnmodifiableCollectionsSerializer.registerSerializers(kryo);
            SynchronizedCollectionsSerializer.registerSerializers(kryo);

            // TODO optimization
            kryo.register(String.class);
            kryo.register(Map.class);
            kryo.register(HashMap.class);
            kryo.register(ArrayList.class);
            kryo.register(LinkedList.class);
            kryo.register(HashSet.class);
            kryo.register(TreeSet.class);
            kryo.register(Hashtable.class);
            kryo.register(Date.class);
            kryo.register(Calendar.class);
            kryo.register(ConcurrentHashMap.class);
            kryo.register(SimpleDateFormat.class);
            kryo.register(GregorianCalendar.class);
            kryo.register(Vector.class);
            kryo.register(BitSet.class);
            kryo.register(StringBuffer.class);
            kryo.register(StringBuilder.class);
            kryo.register(Object.class);
            kryo.register(Object[].class);
            kryo.register(String[].class);
            kryo.register(byte[].class);
            kryo.register(char[].class);
            kryo.register(int[].class);
            kryo.register(float[].class);
            kryo.register(double[].class);

            return kryo;
        }
    };

    public static Kryo get() {
        return ectype.get();
    }

    public static void main(String[] args) {
        Output output = new Output(2048);
        get().writeObjectOrNull(output, "admin", String.class);
//        get().writeObjectOrNull(output, map, Map.class);
        get().writeObjectOrNull(output, null, Object.class);
        get().writeObjectOrNull(output, 123, int.class);
        byte[] bytes = output.toBytes();
        Input input = new Input(bytes);
        String s = get().readObjectOrNull(input, String.class);
        HashMap map1 = get().readObjectOrNull(input, HashMap.class);
        Integer a = get().readObjectOrNull(input, int.class);
        System.err.println(s);
        System.err.println(map1);
        System.err.println(a);
    }
}
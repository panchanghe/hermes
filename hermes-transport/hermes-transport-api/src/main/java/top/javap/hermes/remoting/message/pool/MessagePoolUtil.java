package top.javap.hermes.remoting.message.pool;

import top.javap.hermes.remoting.message.Request;
import top.javap.hermes.remoting.message.Response;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class MessagePoolUtil {

    private static final RequestPool requestPool = new RequestPool();
    private static final ResponsePool responsePool = new ResponsePool();

    public static Request getRequest() {
        return requestPool.get();
    }

    public static Response getResponse() {
        return responsePool.get();
    }

    public static void release(Object message) {
        if (message instanceof Request) {
            requestPool.release((Request) message);
        } else if (message instanceof Response) {
            responsePool.release((Response) message);
        }
    }
}
package top.javap.hermes.exception;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class RpcException extends RuntimeException {

    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
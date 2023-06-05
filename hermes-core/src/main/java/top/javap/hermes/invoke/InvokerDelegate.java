package top.javap.hermes.invoke;

import top.javap.hermes.remoting.message.Invocation;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
public class InvokerDelegate implements Invoker {

    private Invoker delegate;

    public InvokerDelegate() {
    }

    public InvokerDelegate(Invoker delegate) {
        this.delegate = delegate;
    }

    public void setDelegate(Invoker delegate) {
        this.delegate = delegate;
    }

    @Override
    public Result invoke(Invocation invocation) {
        return delegate.invoke(invocation);
    }
}
package top.javap.hermes.invoke;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/30
 **/
public class EmptyResult implements Result {

    public static final EmptyResult INSTANCE = new EmptyResult();

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public boolean hasException() {
        return false;
    }

    @Override
    public Object recreate() {
        return null;
    }
}
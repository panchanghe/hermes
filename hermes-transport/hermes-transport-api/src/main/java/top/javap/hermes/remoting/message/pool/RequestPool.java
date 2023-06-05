package top.javap.hermes.remoting.message.pool;

import io.netty.util.Recycler;
import top.javap.hermes.pool.ObjectPool;
import top.javap.hermes.remoting.message.Request;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class RequestPool implements ObjectPool<Request> {

    private static final Recycler<Request> REQUEST_RECYCLER = new Recycler<Request>() {
        protected Request newObject(Recycler.Handle<Request> handle) {
            return new RecycleRequest(handle);
        }
    };

    @Override
    public Request get() {
        return REQUEST_RECYCLER.get();
    }

    @Override
    public void release(Request object) {
        if (object instanceof RecycleRequest) {
            ((RecycleRequest) object).recycle();
        }
    }

    private static class RecycleRequest extends Request {

        private final Recycler.Handle<Request> handle;

        private RecycleRequest(Recycler.Handle<Request> handle) {
            this.handle = handle;
        }

        private void recycle() {
            this.setOneWay(false);
            this.setEventType(0);
            this.setRequestId(nextId());
            this.setBodyLength(0);
            this.setBody(null);
            handle.recycle(this);
        }
    }
}
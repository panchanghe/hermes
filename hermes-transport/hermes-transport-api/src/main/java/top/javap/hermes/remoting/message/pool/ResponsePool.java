package top.javap.hermes.remoting.message.pool;

import io.netty.util.Recycler;
import top.javap.hermes.pool.ObjectPool;
import top.javap.hermes.remoting.message.Response;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ResponsePool implements ObjectPool<Response> {

    private static final Recycler<Response> RESPONSE_RECYCLER = new Recycler<Response>() {
        protected Response newObject(Recycler.Handle<Response> handle) {
            return new RecycleResponse(handle);
        }
    };


    @Override
    public Response get() {
        return RESPONSE_RECYCLER.get();
    }

    @Override
    public void release(Response object) {
        if (object instanceof RecycleResponse) {
            ((RecycleResponse) object).recycle();
        }
    }

    private static class RecycleResponse extends Response {

        private final Recycler.Handle<Response> handle;

        private RecycleResponse(Recycler.Handle<Response> handle) {
            this.handle = handle;
        }

        private void recycle() {
            this.setRequestId(0);
            this.setStatus((byte) 0);
            this.setBodyLength(0);
            this.setBody(null);
            handle.recycle(this);
        }
    }
}
package network;

/**
 * Created by hua on 2017/8/16.
 * 网络请求回调
 */
public interface IResponseListener<T> {

    void onResponse(T result);

    void onError(Exception e);

}

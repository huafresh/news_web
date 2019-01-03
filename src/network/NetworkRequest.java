package network;

import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * 网络请求封装
 */

public class NetworkRequest {

    private RequestBean mRequestBean;
    private IResponseListener<JSONObject> mResponseListener;


    public static NetworkRequest with() {
        return new NetworkRequest();
    }

    public static NetworkRequest with(RequestBean bean) {
        return new NetworkRequest(bean);
    }

    public NetworkRequest() {
        this(null);
    }

    private NetworkRequest(RequestBean bean) {
        if (bean == null) {
            mRequestBean = new RequestBean();
        } else {
            mRequestBean = bean;
        }
    }


    /**
     * 发起网络请求
     */
    public void request() {
        if (mResponseListener != null) {
            NetWorkService.getInstance().request(mRequestBean, mResponseListener);
        } else {
            NetWorkService.getInstance().request(mRequestBean, mRequestBean.getResponseListener());
        }
    }

    
    

//    public void multiPartRequest(String url, HashMap<String, FileBody> file,
//                                 HashMap<String, String> params) {
//        multiPartRequest(url, file, params, 10000, 1);
//    }

//    /**
//     * 上传图片
//     *
//     * @param url     上传地址
//     * @param file    封装要上传的文件为{@link FileBody}，然后以key="head"存储进file
//     * @param params  上传请求参数
//     * @param timeout 超时时间
//     * @param retry   重试次数
//     */
//    public void multiPartRequest(String url, HashMap<String, FileBody> file,
//                                 HashMap<String, String> params, int timeout, int retry) {
//        if (mTkResponseListener != null) {
//            HttpService.getInstance().multiPartRequest(url, file, params, timeout, retry, mTkResponseListener);
//        } else if (mResponseListener != null) {
//            HttpService.getInstance().multiPartRequest(url, file, params, timeout, retry, mResponseListener);
//        } else {
//            throw new RuntimeException("NetRequest : there is no response listener!!!");
//        }
//    }

    public NetworkRequest responseListener(IResponseListener<JSONObject> listener) {
        mResponseListener = listener;
        return this;
    }

    public NetworkRequest params(HashMap<String, String> params) {
        mRequestBean.setParams(params);
        return this;
    }

    public NetworkRequest heads(HashMap<String, String> heads) {
        mRequestBean.setHeads(heads);
        return this;
    }

//    public NetworkRequest shouldCache(boolean shouldCache) {
//        mRequestBean.setShouldCache(shouldCache);
//        return this;
//    }
//
//    public NetworkRequest cacheTimeout(long cacheTimeout) {
//        mRequestBean.setCacheTimeout(cacheTimeout);
//        return this;
//    }
//
//    public NetworkRequest cacheType(CacheType cacheType) {
//        mRequestBean.setCacheType(cacheType);
//        return this;
//    }
//
//    public NetworkRequest urlName(String urlName) {
//        mRequestBean.setUrlName(urlName);
//        return this;
//    }

//    public NetworkRequest tag(String tag) {
//        mRequestBean.setTag(tag);
//        return this;
//    }
//
//    public NetworkRequest shouldSign(boolean shouldSign) {
//        mRequestBean.setShouldSign(shouldSign);
//        return this;
//    }
//
//    public NetworkRequest signKey(String signKey) {
//        mRequestBean.setSignKey(signKey);
//        return this;
//    }
//
//    public NetworkRequest shouldEncrypt(boolean shouldEncrypt) {
//        mRequestBean.setShouldEncrypt(shouldEncrypt);
//        return this;
//    }
//
//    public NetworkRequest encryptMode(String encryptMode) {
//        mRequestBean.setEncryptMode(encryptMode);
//        return this;
//    }
//
//    public NetworkRequest encryptKey(String encryptKey) {
//        mRequestBean.setEncryptKey(encryptKey);
//        return this;
//    }


    public NetworkRequest url(String url) {
        mRequestBean.setUrl(url);
        return this;
    }

    public NetworkRequest method(RequestBean.Method method) {
        mRequestBean.setMethod(method);
        return this;
    }


    public NetworkRequest suffix(String suffix) {
        mRequestBean.setUrlSuffix(suffix);
        return this;
    }
}

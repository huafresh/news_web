package network;

import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * Created by hua on 2017/8/16.
 */
public class RequestBean {

    enum Method {
        GET,
        POST
    }

    private String url;
    private String urlSuffix;
    private Method method;
    private HashMap<String, String> heads;
    private HashMap<String, String> params;
    private int timeOut;
    private IResponseListener<JSONObject> responseListener;

    public String getUrlSuffix() {
        return urlSuffix;
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    public HashMap<String, String> getHeads() {
        return heads;
    }

    public void setHeads(HashMap<String, String> heads) {
        this.heads = heads;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public IResponseListener<JSONObject> getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(IResponseListener<JSONObject> responseListener) {
        this.responseListener = responseListener;
    }
}

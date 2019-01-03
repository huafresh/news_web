package network;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import utils.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by hua on 2017/8/5.
 * 网络请求服务
 */
public class NetWorkService {

    public static final String TAG = "NetWorkService";

    public static NetWorkService getInstance() {
        return HOLDER.sInstance;
    }

    private static final class HOLDER {
        private static final NetWorkService sInstance = new NetWorkService();
    }


    public void request(final RequestBean requestBean, final IResponseListener<JSONObject> responseListener) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteOut = null;
        try {
            HttpURLConnection connection = resolveGetConnection(requestBean);
            if (connection == null) {
                return;
            }
            connection.connect();
            inputStream = connection.getInputStream();
            byteOut = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (((len = inputStream.read(buffer)) != -1)) {
                byteOut.write(buffer, 0, len);
            }

            String json = byteOut.toString("utf-8");
            JSONObject jsonObject = JSONObject.fromObject(json);
            if (responseListener != null) {
                if (jsonObject != null) {
                    responseListener.onResponse(jsonObject);
                } else {
                    responseListener.onError(new Exception("网络出错"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteOut != null) {
                try {
                    byteOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private HttpURLConnection resolveGetConnection(RequestBean requestBean) {
        HttpURLConnection connection = null;
        try {
            //构造url
            String urlString = requestBean.getUrl();
            if (TextUtils.isEmpty(urlString)) {
                return null;
            }

            HashMap<String, String> params = requestBean.getParams();
            if (params != null) {

                urlString = formatUrlEnd(urlString);
                StringBuilder builder = new StringBuilder(urlString);

                builder.append("?");
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    builder.append(key);
                    builder.append("=");
                    builder.append(value);
                    builder.append("&");

                }
                urlString = builder.toString();
                if (urlString.endsWith("&")) {
                    urlString = urlString.substring(0, urlString.length() - 1);
                }
            }

            //添加后缀
            if (!TextUtils.isEmpty(requestBean.getUrlSuffix())) {
                urlString += requestBean.getUrlSuffix();
            }

            //建立连接
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            //设置head
            HashMap<String, String> headers = requestBean.getHeads();
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    connection.setRequestProperty(key, value);
                }
            }

            //设置请求方式
            if (requestBean.getMethod() == RequestBean.Method.GET) {
                connection.setRequestMethod("GET");
            } else if (requestBean.getMethod() == RequestBean.Method.POST) {
                connection.setRequestMethod("POST");
            } else {
                connection.setRequestMethod("GET");
            }

            //初始化默认值
            if (requestBean.getTimeOut() == 0) {
                requestBean.setTimeOut(5000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    private String formatUrlEnd(String url) {
        if (url.endsWith("/") || url.endsWith("?")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

}

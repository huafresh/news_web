package servelets;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/8.
 */
public class ResponseUtil {

    /**
     * 根据对象构造返回的json数据
     *
     * @param object 需要返回的对象
     */
    public static JSONObject buildJsonResponse(Object object) throws IOException {
        return buildJsonResponse(null, object);
    }

    /**
     * 根据对象构造返回的json数据
     *
     * @param results 控制返回的错误信息
     * @param object  需要返回的对象列表
     */
    public static JSONObject buildJsonResponse(String[] results, Object object) throws IOException {
        JSONObject resultJson = new JSONObject();
        if (object != null) {
            resultJson.put("error_no", 0);
        } else {
            resultJson.put("error_no", -1);
        }
        if (results != null) {
            resultJson.put("error_info", results[1]);
        } else {
            if (object == null) {
                resultJson.put("error_info", "服务器异常");
            }
        }

        try {
            JSONArray jsonArray = JSONArray.fromObject(object);
            resultJson.put("results", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.put("error_info","服务器异常");
        }

        return resultJson;
    }

    public static JSONObject buildJsonResponse(int error_no, String error_info) throws IOException {
        JSONObject resultJson = new JSONObject();
        resultJson.put("error_no", error_no);
        resultJson.put("error_info", error_info);
        return resultJson;
    }

    /**
     * 请求正常返回
     *
     * @param response 用于输出返回数据
     * @param content  返回的内容
     */
    public static void doResponse(HttpServletResponse response, String content) throws IOException {
        response.setStatus(200);
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.print(content);
    }

}

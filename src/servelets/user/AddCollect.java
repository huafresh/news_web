package servelets.user;

import net.sf.json.JSONObject;
import servelets.ResponseUtil;
import user.CollectHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hua on 2017/9/28.
 * 收藏接口。
 */
@WebServlet(name = "AddCollect", urlPatterns = "/collect/add")
public class AddCollect extends HttpServlet {

    private static final String TYPE_ADD = "add";
    private static final String TYPE_QUERY = "query";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_id = request.getHeader("user_id");
        String news_id = request.getHeader("news_id");
        String[] results = new String[2];
        boolean success = false;
        try {
            CollectHelper helper = new CollectHelper();
            success = helper.addUserCollectInfo(user_id, news_id, results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = ResponseUtil.buildJsonResponse(success ? 0 : -1, results[1]);
        ResponseUtil.doResponse(response, jsonObject.toString());
    }
}

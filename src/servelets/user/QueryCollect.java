package servelets.user;

import net.sf.json.JSONObject;
import servelets.ResponseUtil;
import user.entitys.CollectEntity;
import user.CollectHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by hua on 2017/9/29.
 */
@WebServlet(name = "QueryCollect", urlPatterns = "/collect/query")
public class QueryCollect extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = request.getHeader("user_id");
        List<CollectEntity> collectEntities = null;
        try {
            CollectHelper helper = new CollectHelper();
            collectEntities = helper.queryUserCollectInfo(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = ResponseUtil.buildJsonResponse(collectEntities);
        ResponseUtil.doResponse(response, jsonObject.toString());
    }
}

package servelets.news;

import net.sf.json.JSONObject;
import servelets.ResponseUtil;
import user.CollectHelper;
import user.ColumnHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取栏目列表信息接口。
 * 此接口处理三种业务场景：
 * 1、查询当前用户的栏目列表信息
 * 2、添加一个栏目
 * 3、删除一个栏目
 * 以上业务场景通过type字段进行区分
 * <p>
 * Created by hua on 2017/10/18.
 */
@WebServlet(name = "ColumnList", urlPatterns = "/column")
public class ColumnList extends HttpServlet {

    private static final String TYPE_QUERY = "query";
    private static final String TYPE_ADD = "add";
    private static final String TYPE_REMOVE = "remove";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String user_id = request.getHeader("user_id");
        final String type = request.getHeader("type");
        final String column_id = request.getHeader("column_id");

        String[] results = new String[2];
        String result = null;
        try {
            ColumnHelper helper = new ColumnHelper(getServletContext());
            switch (type) {
                case TYPE_QUERY:
                    result = helper.queryColumnList(user_id, results);
                    break;
                case TYPE_ADD:
                    result = helper.changeColumnState(user_id, column_id, true, results);
                    break;
                case TYPE_REMOVE:
                    result = helper.changeColumnState(user_id, column_id, false, results);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = ResponseUtil.buildJsonResponse(results, result);
        ResponseUtil.doResponse(response, jsonObject.toString());
    }
}

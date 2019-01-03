package servelets.news;

import net.sf.json.JSONObject;
import news.NewsManager;
import news.NormalNewsDetailBean;
import servelets.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hua on 2017/9/18.
 */
@WebServlet(name = "DetailNews", urlPatterns = "/news/normal/detail")
public class DetailNews extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String news_id = request.getHeader("news_id");

        NormalNewsDetailBean bean = null;

        try {
            bean = NewsManager.getInstance().getNormalNewsDetail(news_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = ResponseUtil.buildJsonResponse(bean);
        ResponseUtil.doResponse(response, jsonObject.toString());
    }
}

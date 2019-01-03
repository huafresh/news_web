package servelets.news;

import news.NewsManager;
import news.NormalNewsPreviewBean;
import servelets.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by hua on 2017/8/18.
 */
@WebServlet(name = "PreviewNews", urlPatterns = "/news/normal/preview")
public class PreviewNews extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String channel = request.getHeader("channel");
        String count = request.getHeader("count");
        String isPullUp = request.getHeader("isPullUp");
        String time = request.getHeader("time");

        List<NormalNewsPreviewBean> resultList = null;
        try {
            resultList = NewsManager.getInstance().getNormalNewsPreviewList(channel,
                    Integer.valueOf(count), isPullUp.equals("1"), time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ResponseUtil.doResponse(response,ResponseUtil.buildJsonResponse(resultList).toString());
    }
}

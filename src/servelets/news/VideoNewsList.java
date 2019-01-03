package servelets.news;

import data.entitys.VideoNewsEntity;
import db.DBConstant;
import net.sf.json.JSONObject;
import news.NewsManager;
import servelets.ResponseUtil;
import utils.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hua
 * @version 2017/11/22 16:23
 */
@WebServlet(name = "VideoNewsList", urlPatterns = "/video")
public class VideoNewsList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String count = request.getHeader("count");
        String time = request.getHeader("time");
        boolean isPullUp = "1".equals(request.getHeader("isPullUp"));

        List<VideoNewsEntity> videoList = NewsManager.getInstance().getVideoNewsList(Integer.valueOf(count), isPullUp, time);

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(videoList);

        ResponseUtil.doResponse(response, jsonObject.toString());
    }
}

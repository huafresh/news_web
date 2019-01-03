package servelets.user.login;

import user.LoginHelper;
import user.entitys.LoginUserInfo;
import net.sf.json.JSONObject;
import servelets.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hua on 2017/8/8.
 */
@WebServlet(name = "GetUserInfo",urlPatterns = "/user_info")
public class GetUserInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = request.getHeader("user_id");

        String[] result = new String[2];
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = LoginHelper.getInstance().getUserInfo(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result[1] = loginUserInfo == null ? "获取用户信息失败" : "获取用户信息成功";

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(result, loginUserInfo);
        ResponseUtil.doResponse(response,jsonObject.toString());
    }
}

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
 * Created by hua on 2017/8/5.
 */
@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String account = request.getHeader("account");
        String pwd = request.getHeader("password");
        String loginType = request.getHeader("login_type");

        String[] result = new String[2];
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = LoginHelper.getInstance().login(account, pwd, loginType, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(result, loginUserInfo);
        ResponseUtil.doResponse(response, jsonObject.toString());

    }


}

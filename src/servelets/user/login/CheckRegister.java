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
@WebServlet(name = "CheckRegister",urlPatterns = "/check_register")
public class CheckRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getHeader("mail");
        String type = request.getHeader("type");

        String[] result = new String[2];

        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = LoginHelper.getInstance().checkRegister(account,type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(result, loginUserInfo);
        ResponseUtil.doResponse(response,jsonObject.toString());
    }
}
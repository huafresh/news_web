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
@WebServlet(name = "Register",urlPatterns = "/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getHeader("mail");
        String phone = request.getHeader("phone");
        String password = request.getHeader("password");

        System.out.println(mail + "," + phone + "," + password);

        String[] result = new String[2];
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = LoginHelper.getInstance().register(mail, phone, password, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(result, loginUserInfo);
        ResponseUtil.doResponse(response,jsonObject.toString());
    }
}

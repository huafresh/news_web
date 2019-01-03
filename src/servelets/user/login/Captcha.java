package servelets.user.login;

import net.sf.json.JSONObject;
import servelets.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by hua on 2017/8/5.
 */
@WebServlet(name = "Captcha",urlPatterns = "/captcha")
@Deprecated
public class Captcha extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //随机生成四位的验证码
        Random random = new Random();
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int code = random.nextInt(9);
            verifyCode.append(code);
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("error_no", 0);
        resultJson.put("error_info", "获取短信验证码成功");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("captcha",verifyCode.toString());
        resultJson.put("results",jsonObject);

        ResponseUtil.doResponse(response,resultJson.toString());

    }
}

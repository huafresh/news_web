package servelets.user.login;

import utils.UploadHelper;
import user.LoginHelper;
import user.entitys.LoginUserInfo;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import servelets.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by hua on 2017/8/11.
 */
@WebServlet(name = "ModifyAvatar", urlPatterns = "/modify/avatar")
public class ModifyAvatar extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ServletFileUpload.isMultipartContent(request)) { //过滤非上传请求
            return;
        }

        String userId = request.getHeader("user_id");

        ServletFileUpload upload = UploadHelper.getServletFileUpload(getServletContext());
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long read, long length, int i) {
                System.out.println("文件大小为" + length + ",当前已处理" + read);
            }
        });

        String[] results = new String[2];
        LoginUserInfo loginUserInfo = null;
        try {
            List<FileItem> itemList = upload.parseRequest(request);
            FileItem fileItem = itemList.get(0); //上传多个头像时，只取第一个
            loginUserInfo = LoginHelper.getInstance().modifyAvatar(getServletContext(), userId, fileItem, results);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = ResponseUtil.buildJsonResponse(results, loginUserInfo);
        ResponseUtil.doResponse(response, jsonObject.toString());
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

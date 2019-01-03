package utils;

import constants.Constant;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.FileUtils;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * 上传文件帮助类
 * Created by hua on 2017/8/12.
 */
public class UploadHelper {

    /**
     * 获取服务器的上传缓存仓库
     * @param context 服务器上下文
     * @return 缓存仓库对象 - ServletFileUpload
     */
    public static ServletFileUpload getServletFileUpload(ServletContext context) {
        File cacheFileDir = FileUtils.getDirFile(context, Constant.UPLOAD_CACHE_DIR);

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(1024 * 100); //设置缓存区大小
        fileItemFactory.setRepository(cacheFileDir); //设置临时文件缓存目录

        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        upload.setHeaderEncoding("utf-8");
        upload.setFileSizeMax(1024 * 1024); //单个文件最大1M
        upload.setSizeMax(1024 * 1024 * 10); //多个文件总共最大10M

        return upload;
    }

}

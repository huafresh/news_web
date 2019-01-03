package utils;

import utils.TextUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具
 * Created by hua on 2017/8/12.
 */
public class FileUtils {

    /**
     * 获取目录的File对象，如果上级目录不存在会自动创建
     *
     * @param context 上下文
     * @param path    相对路径
     * @return File对象
     */
    public static File getDirFile(ServletContext context, String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        String realPath = context.getRealPath(path);
        File file = new File(realPath);
        if (!file.exists()) {
            boolean b = file.mkdirs();
            if (!b) {
                return null;
            }
        }
        return file;
    }

    /**
     * 获取文件的File对象，如果上级目录不存在会自动创建
     *
     * @param context 上下文
     * @param path    相对路径
     * @return File对象
     */
    public static File getFile(ServletContext context, String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        String realPath = context.getRealPath(path);
        File file = new File(realPath);
        File dirFile = file.getParentFile();
        if (!file.exists()) {
            if (!dirFile.exists()) {
                boolean b = dirFile.mkdirs();
                if (!b) {
                    return null;
                }
            }
            try {
                boolean ret = file.createNewFile();
                if (ret) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}

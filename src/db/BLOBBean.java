package db;

import java.io.InputStream;

/**
 * Created by hua on 2017/8/12.
 * 向数据库中插入BLOB类型数据时，使用此类传入相关的参数
 */
public class BLOBBean {
    private InputStream inputStream;
    private int length;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

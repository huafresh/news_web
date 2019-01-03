package user;

import constants.Constant;
import db.*;
import db.handers.HandlerToBeanList;
import db.handers.HandlerToListMap;
import user.entitys.LoginUserInfo;
import utils.FileUtils;
import org.apache.commons.fileupload.FileItem;
import utils.TextUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * 登录相关接口业务处理帮助类
 * Created by hua on 2017/7/20.
 */
public class LoginHelper {

    private final String tableName;
    private final CRUDAndroid mSQLiteBase;

    private LoginHelper() {
        tableName = DBConstant.User.TABLE_NAME;
        mSQLiteBase = new CRUDAndroid();
    }

    public static LoginHelper getInstance() {
        return new LoginHelper();
    }

    /**
     * 修改昵称
     *
     * @param userId  用户id
     * @param newName 新昵称
     * @return 是否成功
     */
    public LoginUserInfo modifyNickName(String userId, String newName, String[] result) {

        LoginUserInfo loginUserInfo = null;
        String errorInfo = "";

        if (!TextUtils.isEmpty(newName)) {
            HashMap<String, Object> values = new HashMap<>();
            values.put(DBConstant.User.COLUMN_NICK_NAME, newName);
            long sum = mSQLiteBase.update(tableName, values, new String[]{DBConstant.User.COLUMN_USER_ID}, new String[]{userId});
            if (sum > 0) {
                loginUserInfo = getUserInfo(userId);
                if (loginUserInfo != null) {
                    errorInfo = "昵称修改成功";
                }
            }
        } else {
            errorInfo = "昵称不能为空";
        }

        return methodReturn(result, errorInfo, loginUserInfo);
    }

    public LoginUserInfo modifyAvatar(ServletContext context, String userId, FileItem fileItem, String[] results) {

        String error_info = "";
        LoginUserInfo loginUserInfo = null;

        if (!fileItem.isFormField()) { //判断fileItem中封装的是不是文件
            String fileName = fileItem.getName();
            if (!TextUtils.isEmpty(fileName)) {
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (fileExtName.equals("jpeg") || fileExtName.equals("png") || fileExtName.equals("jpg")) {

                    String relativePath = Constant.AVATAR_DIR + userId + "_" + fileName;
                    File avatarFile = FileUtils.getFile(context, relativePath);
                    if (avatarFile != null) {
                        byte[] buffer = new byte[1024];
                        try {
                            InputStream inputStream = fileItem.getInputStream();
                            FileOutputStream outputStream = new FileOutputStream(avatarFile);
                            int len = 0;
                            while ((len = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return methodReturn(results, error_info, null);
                        }

                        HashMap<String, Object> values = new HashMap<>();
                        context.getContextPath();
                        values.put("avatar_path", relativePath);
                        long sum = mSQLiteBase.update(tableName, values, new String[]{DBConstant.User.COLUMN_USER_ID},
                                new String[]{userId});
                        if (sum > 0) {
                            loginUserInfo = getUserInfo(userId);
                            if (loginUserInfo != null) {
                                error_info = "头像上传成功";
                            }
                        }
                    }
                } else {
                    error_info = "上传的文件类型不支持";
                }
            } else {
                error_info = "上传失败，上传的文件名不能为空";
            }
        } else {
            error_info = "上传的不是File";
        }

        return methodReturn(results, error_info, loginUserInfo);
    }

    /**
     * 通过user_id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息bean
     */
    public LoginUserInfo getUserInfo(String userId) {
        return getUserInfo(DBConstant.User.COLUMN_USER_ID, userId);

    }

    //根据某一唯一值查询整个用户信息
    private LoginUserInfo getUserInfo(String columnName, Object value) {
        List<LoginUserInfo> list = mSQLiteBase.query(tableName, null, new String[]{columnName}, new Object[]{value},
                new HandlerToBeanList(LoginUserInfo.class));
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 自动登录
     *
     * @param userId 用户id
     * @return 用户信息bean
     */
    public LoginUserInfo autoLogin(String userId) {
        return getUserInfo(userId);
    }


    /**
     * 检查邮箱或者手机号是否注册
     *
     * @param account 用户id
     * @param type    类型
     *                201 表示判断手机号
     *                202 表示判断邮箱
     * @return 用户信息bean
     */
    public LoginUserInfo checkRegister(String account, String type) {
        if (type.equals("201")) {
            return getUserInfo(DBConstant.User.COLUMN_BIND_PHONE, account);
        } else if (type.equals("202")) {
            return getUserInfo(DBConstant.User.COLUMN_MAIL, account);
        }
        return null;
    }

    /**
     * 登录
     *
     * @param account   账号 / 第三方平台唯一码
     * @param password  密码 / 第三方平台名称
     * @param loginType 账号类型
     * @param result    Result[0]存放状态码，Result[1]存放状态信息
     * @return 登录成功返回用户信息，其他情况返回null
     */
    public LoginUserInfo login(String account, String password, String loginType, Object[] result) {
        String errorInfo = "";
        LoginUserInfo loginUserInfo = null;

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(loginType)) {
            long id = verifyAccount(loginType, account, password);

            if (id == -1) {
                errorInfo = "账号 : " + account + "不存在";
            } else if (id == -2) {
                errorInfo = "密码错误";
            } else {
                LoginUserInfo info = getUserInfo(String.valueOf(id));
                if (info != null) {
                    errorInfo = "登录成功";
                    loginUserInfo = info;
                }
            }

            //到此说明一般账户登录失败，但对于第三方登录，登录失败意味着没有注册，因此执行静默注册
            if (loginType.equals(Constant.LOGIN_TYPE_THIRD)) {

                HashMap<String, Object> values = new HashMap<>();
                values.put(DBConstant.User.COLUMN_THIRD_UNIQUE_ID, account);
                values.put(DBConstant.User.COLUMN_THIRD_PLAT, password);
                long sum = mSQLiteBase.insert(tableName, null, values);

                if (sum > 0) { //插入成功
                    errorInfo = "登录成功";
                    loginUserInfo = getUserInfo(DBConstant.User._ID, sum);
                }
            }
        } else {
            errorInfo = "账号密码不能为空";
        }

        return methodReturn(result, errorInfo, loginUserInfo);
    }

    private LoginUserInfo methodReturn(Object[] result, String errorInfo, LoginUserInfo loginUserInfo) {
        if (TextUtils.isEmpty(errorInfo)) {
            errorInfo = "服务器异常";
        }

        if (result != null && result.length > 1) {
            result[1] = errorInfo;
        }

        return loginUserInfo;
    }


    /**
     * 注册账户
     *
     * @param mail     邮箱
     * @param phone    手机号
     * @param password 密码
     * @param result   Result[0]存放状态码，Result[1]存放状态信息
     * @return 登录成功返回用户信息，其他情况返回null
     */
    public LoginUserInfo register(String mail, String phone, String password, String[] result) {

        String errorInfo = "";
        LoginUserInfo loginUserInfo = null;

        if (!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(password)) {
            if (isExist(DBConstant.User.COLUMN_MAIL, mail)) {
                errorInfo = "该邮箱已经注册过，请直接登录";
            } else if (!TextUtils.isEmpty(phone) && isExist(DBConstant.User.COLUMN_BIND_PHONE, phone)) {
                errorInfo = "该手机号已经注册过，请直接登录";
            } else {
                HashMap<String, Object> values = new HashMap<>();
                values.put(DBConstant.User.COLUMN_MAIL, mail);
                values.put(DBConstant.User.COLUMN_BIND_PHONE, phone);
                values.put(DBConstant.User.COLUMN_PASSWORD, password);

                long id = mSQLiteBase.insert(tableName, null, values);
                if (id > 0) { //插入成功
                    values.clear();
                    //设置用户id
                    values.put(DBConstant.User.COLUMN_USER_ID, generateUserId(id));
                    long sum = mSQLiteBase.update(tableName, values, new String[]{DBConstant.User._ID}, new Object[]{id});
                    if (sum > 0) {
                        //查询出用户信息
                        loginUserInfo = getUserInfo(DBConstant.User._ID, id);
                        if (loginUserInfo != null) {
                            errorInfo = "注册成功";
                        }
                    }
                }
            }
        } else {
            errorInfo = "邮箱或密码不能为空";
        }

        return methodReturn(result, errorInfo, loginUserInfo);
    }

    /**
     * 判断所给的列名和值在数据库中是否存在
     */
    private boolean isExist(String column, String value) {
        Object result = mSQLiteBase.query(tableName, null, new String[]{column},
                new Object[]{value}, new HandlerToBeanList(LoginUserInfo.class));
        return result != null;
    }


    /**
     * 构造唯一的userId
     *
     * @return 失败返回null；成功返回UserId
     */
    private String generateUserId(long id) {
        if (id > 0) {
            return (10000 + id) + "";
        }
        return null;
    }


    /**
     * 根据不同的type校验给定的账号在数据库中是否存在，同时对比密码是否一致。
     *
     * @return 账号不存在返回-1；密码错误返回-2；登录类型错误返回-3，成功，返回userId
     */
    private long verifyAccount(String type, String account, String password) {
        List<HashMap<String, Object>> result = null;
        switch (type) {
            case Constant.LOGIN_TYPE_PHONE:
                result = mSQLiteBase.query(tableName, new String[]{DBConstant.User.COLUMN_PASSWORD, DBConstant.User.COLUMN_USER_ID},
                        new String[]{DBConstant.User.COLUMN_BIND_PHONE}, new String[]{account}, new HandlerToListMap());
                break;
            case Constant.LOGIN_TYPE_MAIL:
                result = mSQLiteBase.query(tableName, new String[]{DBConstant.User.COLUMN_PASSWORD, DBConstant.User.COLUMN_USER_ID},
                        new String[]{DBConstant.User.COLUMN_MAIL}, new String[]{account}, new HandlerToListMap());
                break;
            case Constant.LOGIN_TYPE_THIRD:
                result = mSQLiteBase.query(tableName, new String[]{DBConstant.User.COLUMN_PASSWORD, DBConstant.User.COLUMN_USER_ID},
                        new String[]{DBConstant.User.COLUMN_THIRD_UNIQUE_ID}, new String[]{account}, new HandlerToListMap());
                break;
            default:
                return -3;
        }
        if (result == null) {
            return -1;
        }
        String pwd = (String) result.get(0).get(DBConstant.User.COLUMN_PASSWORD);
        if (!pwd.equals(password)) {
            return -2;
        }
        String userId = (String) result.get(0).get(DBConstant.User.COLUMN_USER_ID);
        return Integer.valueOf(userId);
    }

}

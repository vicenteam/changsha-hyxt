package com.stylefeng.guns.modular.api.apiparam;



import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/4/14.
 */
public class StaticVariable {
    //默认环信密码
    public static final String DEFAUTL_PASSWORD = "123456";
    //静态字符
    public static final String STATIC_OK="OK";

    public static final String STATIC_ZERO="0";
    public static final String STATIC_ONE="1";
    public static final String STATIC_TWO="2";
    /**聊天室信息**/
    public static final int ROOM_CAPACITY=5000;
    public static final String ROOM_OWNER="1";
    public static final String ROOM_DESCRIPTION="大厅聊天室";

    public static final String SESSION_KEY_USER="userInfoLogin";

    public static final String REQUEST_SUCCESS = "200"; //成功
    public static final String REQUEST_FAILURE = "401"; //失败
    public static final String REQUEST_OVERDUE = "402"; //过期失效
    public static final String REQUEST_DELETION = "403";    //参数不全
    public static final String REQUEST_NULL = "404";    //未找到相关数据
    public static final String REQUEST_JUMP = "405"; //失败
    public static final String REQUEST_ERROR = "500";   //系统异常



    public static final String CURRENTPAGE = "0";   //默认当前页
    public static final String TOTALPAGE = "0";   //默认总页数




    public static final String MSG_SUCCESS = "成功";
    public static final String MSG_FAILURE = "失败";
    public static final String MSG_ERROR = "异常";
    public static final String MSG_NULL = "未查询到相关数据";
    public static final String MSG_DELETION = "参数缺失||无效参数";
    public static final String OPERATION_SUCCESS = "操作成功";
    public static final String OPERATION_FAILURE= "操作失败";
    public static final String OPERATION_ERROR= "操作异常";
    public static final String OPERATION_DELETION= "参数缺失||无效参数";
    public static final String OPERATION_NULL= "未查询到相关数据";

    //极光
    public static final String MSG_SYSTEM_TITLE = "系统";
    public static final String MSG_JION_TITLE = "你有新的加入活动请求";
    public static final String MSG_ATTENTION_TITLE = "你有新的好友请求";
    public static final String MSG_WORDS_TITLE = "你有新的消息";

    public static final String JION_CONTEXT = "请求加入";
    public static final String ATTENTION_CONTEXT = "请求加为好友";
    public static final String SYMBOL = "#";
    public static final String ACTIVITY="活动";




    public static final String CLATIA_DATA="/deploy/clatia_data";
    //头像图片
    public static final String USER_HEAD_DIR="/head_data/";
    //背景图片
    public static final String USER_BACKGROUD_DIR="/imgResources/user/backgroud/";

    public static final String COMMA = ",";

    public static final String LOGO_NAME = "骑行天下";

    public static final SimpleDateFormat formatHMS =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat formatHM =  new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
    //用户ID的最大值
    public static final long maxUser = 999999;
    //用户ID的最小值
    public static final long minUser = 100000;

    //随机数最大值
    public static final long RANDOM_MAX = 999999;

    //随机数最小值
    public static final long RANDOM_MIN = 100000;


    //验证码的最大值
    public static final long maxVerify = 9999;
    //验证码的最小值
    public static final long minVerify = 1000;
    //验证码5分钟失效
    public static final long timeVerify = 5*60*1000;

    //默认头像
    public static final String DEFAUTL_HEAD="" ;//ManageInterfaces.FILE_HEAD+getFilePath("1")+"head.jpg";
    //默认背景图片
    public static final String DEFAUTL_BACKGROUND="/imgResources/user/default_img/background.jpg";
    //默认签名
    public static final String DEFAUTL_SIGNATURE="这家伙很懒,什么也没留下!";

    public static String getMSG_SUCCESS(StringBuffer str){
        return str.append(MSG_SUCCESS).toString();
    }

    public static String getMSG_FAILURE(StringBuffer str){
        return str.append(MSG_FAILURE).toString();
    }
    public static String getMSG_ERROR(StringBuffer str){
        return str.append(MSG_ERROR).toString();
    }
    public static String getMSG_NULL(StringBuffer str){
        return str.append(MSG_NULL).toString();
    }
    public static String getMSG_DELETION(StringBuffer str){
        return str.append(MSG_DELETION).toString();
    }

    /**
     * 获取图片存放文件夹
     * @param isok
     * @return
     */
    public static String getFilePath(String isok){
        String path = "";
        switch (Integer.parseInt(isok)){
            case 1://用户头像
                path = USER_HEAD_DIR;
                break;
        }
        return  path;
    }
}

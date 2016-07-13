package com.iwind.red_apple.Constant;

/**
 * 作者：HuGuoJun
 * 2016/6/7 12:59
 * 邮箱：www.guojunkuaile@qq.com
 */
public class ConstantUrl {
    //基地址
    public static final String BASE_URL = "http://119.254.102.77:8071/af_interface/";

    //登陆
    public static final String LOGIN = "api/login";

    //注册
    public static final String REGISTER = "api/regist";

    //登出
    public static final String LOGOUT = "api/logout";

    //发送注册短信验证码
    public static final String SEND_SMSCODE = "sms/regist/sendSmsCode";

    //轮播图
    public static final String GET_IMAGE_TURN = "main/getCarousel";

    //税务页面获取最新条数
    public static final String GET_NEW = "main/getNew";

    //获取个人信息
    public static final String GET_PERSONAL_INFO = "api/users/userinfo";

    //获取我的提问
    public static final String GET_MINE_QUESTION = "forum/searchForum";

    //获取我的收藏
    public static final String GET_MINE_COLLECTION = "main/collection";

    //获取我的回答
    public static final String GET_MINE_ANSWER = "forum/searchForumMessage";

    //上传头像
    public static final String UPLOAD_USER_ICON = "upload/uploadPic";

    //用户反馈
    public static final String USER_CALLBACK = "main/setFeedback";

    //获得模块页面
    public static final String GET_MODEL_PAGE = "main/getPage";

    //查询讨论列表
    public static final String GET_DISCUSS_LIST = "api/forum/Search";

    //获取讨论详情
    public static final String GET_DISCUSS_DETAIL = "forum/getForum";

    //查询新闻列表
    public static final String GET_NEWS_LIST = "api/news/search";


}

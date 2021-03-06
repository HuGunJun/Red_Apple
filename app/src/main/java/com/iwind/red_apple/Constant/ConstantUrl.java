package com.iwind.red_apple.Constant;

/**
 * 作者：HuGuoJun
 * 2016/6/7 12:59
 * 邮箱：www.guojunkuaile@qq.com
 */
public class ConstantUrl {

    public static final String BASE = "http://119.254.102.77:8071/";
//    public static final String BASE = "http://192.168.0.43:8080/";

    //基地址
    public static final String BASE_URL = BASE + "af_interface/";

    //登陆
    public static final String LOGIN = "api/login";

    //注册
    public static final String REGISTER = "api/regist";

    //登出
    public static final String LOGOUT = "api/logout";

    //发送注册短信验证码
    public static final String SEND_SMSCODE = "sms/regist/sendSmsCode";

    //发送忘记密码验证码
    public static final String SENDFORGOTPASS = "sms/pass/sendSmsCode";

    //忘记密码
    public static final String FORGOTPASS = "api/forgetPassword";

    //网络接口修改密码
    public static final String CHAGE_PASS = "api/resetPassword";

    //轮播图
    public static final String GET_IMAGE_TURN = "main/getCarousel";

    //税务页面获取最新条数
    public static final String GET_NEW = "main/getNew";

    //获取个人信息
    public static final String GET_PERSONAL_INFO = "api/users/userinfo";

    //获取我的提问
    public static final String GET_MINE_QUESTION = "forum/searchForum";

    //收藏
    public static final String COLLECTION = "main/collection";

    //我的收藏
    public static final String GET_MINE_COLLECTION = "main/myCollection";

    //获取我的回答
    public static final String GET_MINE_ANSWER = "forum/searchForumMessage";

    //上传头像
    public static final String UPLOAD_USER_ICON = "upload/uploadPic";

    //用户反馈
    public static final String USER_CALLBACK = "main/setFeedback";

    //修改用户信息
    public static final String UPDATE_USERINFO = "api/users/updateUser";

    //获取系统消息
    public static final String GET_MESSAGE = "main/getTidings";

    //获取提问中的标签
    public static final String GET_LABEL = "label/loadlabel";

    //讨论搜索和获取
    public static final String GET_DISCUSS = "forum/search";

    //用户头像地址
    public static final String USER_PIC = BASE + "pic/userpic/";

    //获取讨论详情页面
    public static final String GET_DISCUSS_DETAIL = "forum/getForum";

    //修改讨论对象 （分享/查看次数增加）
    public static final String UPDATE_DISCUSS = "forum/updateForum";

    //获取回答详情
    public static final String GET_ANSWER_DETAIL = "forum/messageInfo";

    //讨论回答踩赞
    public static final String FORUM_ZAIORCAI = "forum/messageCaiZan";

    //办税须知踩赞
    public static final String DO_TAX_SHOULD_KNOW_CAIORZAN = "work/workCaiZan";

    //客户端踩赞
    public static final String CLIENT_CAIORZAN = "client/clientCaiZan";

    //问题库踩赞
    public static final String PROBLEM_CAIORZAN = "problem/problemCaiZan";

    //修改客户端
    public static final String CLIENT_UPDATE = "client/updateClient";

    //添加讨论（添加问题)
    public static final String ADD_FORUM = "forum/addforum";

    //添加回答
    public static final String ADD_ANSWER = "forum/addmessage";

    //办税须知
    public static final String TAX_SHOULDKNOW = "work/search";

    //获取办税须知详情
    public static final String GET_WORK_DETAIL = "work/getWork";

    //修改办税须知
    public static final String UPDATE_WORK = "work/updateWork";

    //发送办税须知私信
    public static final String SEND_WORK_PERSONAL_INFO = "work/addmessage";

    //发送客户端私信
    public static final String SEND_CLIENT_PERSONAL_INFO = "client/addmessage";

    //搜索客户端
    public static final String GETCLIENT = "client/search";

    //获取客户端
    public static final String GETCLIENTDETAIL = "client/getClient";

    //搜索新闻
    public static final String SEARCH_NEWS = "news/search";

    //获取新闻详情
    public static final String GET_NEWS_DETAIL = "news/getNews";

    //新闻踩赞
    public static final String NEW_CAIORZAN = "news/newsCaiZan";

    //更新新闻
    public static final String UPDATE_NEW = "news/updateNews";

    //搜索问题库
    public static final String SEARCH_PROBLEM = "problem/search";

    //修改问题库
    public static final String UPDATE_PROBLEM = "problem/updateProblem";

    //获取问题详情
    public static final String GET_PROBLEM_DETAIL = "problem/getProblem";

    //新增问题库私信
    public static final String SEND_PROBLEM_PERSONAL_INFO = "problem/addmessage";

    //搜索
    public static final String SEARCH = "main/searchContent";

    //视频地址
    public static final String VIDEO_URL = BASE + "pic";

}

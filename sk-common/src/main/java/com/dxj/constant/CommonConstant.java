package com.dxj.constant;

/**
 * 常量
 *
 * @author Sinkiang
 */
public interface CommonConstant {

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png";


    /**
     * 常用接口
     */
    public static class Url {
        // 免费图床
        public static final String SM_MS_URL = "https://sm.ms/api";
        // IP归属地查询
        public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }

    public static final String RESET_MAIL = "重置邮箱";

    /**
     * 用户正常状态
     */
    int USER_STATUS_NORMAL = 0;

    /**
     * 用户禁用状态
     */
    int USER_STATUS_LOCK = -1;

    /**
     * 普通用户
     */
    int USER_TYPE_NORMAL = 0;

    /**
     * 管理员
     */
    int USER_TYPE_ADMIN = 1;

    /**
     * 全部数据权限
     */
    int DATA_TYPE_ALL = 0;

    /**
     * 自定义数据权限
     */
    int DATA_TYPE_CUSTOM = 1;

    /**
     * 正常状态
     */
    int STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    int STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    public static final int DEL_FLAG_1 = 1;

    /**
     * 未删除标志
     */
    public static final int DEL_FLAG_0 = 0;

    /**
     * 限流标识
     */
    String LIMIT_ALL = "XBOOT_LIMIT_ALL";

    String REGION = "内网IP|内网IP";

    /**
     * 顶部菜单类型权限
     */
    int PERMISSION_NAV = -1;

    /**
     * 页面类型权限
     */
    int PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    int PERMISSION_OPERATION = 1;

    /**
     * 1级菜单父id
     */
    String PARENT_ID = "0";

    /**
     * 0级菜单
     */
    int LEVEL_ZERO = 0;

    /**
     * 1级菜单
     */
    int LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    int LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    int LEVEL_THREE = 3;

    /**
     * 消息发送范围
     */
    int MESSAGE_RANGE_ALL = 0;

    /**
     * 未读
     */
    int MESSAGE_STATUS_UNREAD = 0;

    /**
     * 已读
     */
    int MESSAGE_STATUS_READ = 1;

    /**
     * github登录
     */
    int SOCIAL_TYPE_GITHUB = 0;

    /**
     * qq登录
     */
    int SOCIAL_TYPE_QQ = 1;

    /**
     * 微博登录
     */
    int SOCIAL_TYPE_WEIBO = 2;

    /**
     * 短信验证码key前缀
     */
    String PRE_SMS = "XBOOT_PRE_SMS:";

    /**
     * 邮件验证码key前缀
     */
    String PRE_EMAIL = "XBOOT_PRE_EMAIL:";

    /**
     * 本地文件存储
     */
    int OSS_LOCAL = 0;

    /**
     * 七牛云OSS存储
     */
    int OSS_QINIU = 1;

    /**
     * 阿里云OSS存储
     */
    int OSS_ALI = 2;

    /**
     * 腾讯云COS存储
     */
    int OSS_TENCENT = 3;

    /**
     * MINIO存储
     */
    int OSS_MINIO = 4;

    /**
     * 部门负责人类型 主负责人
     */
    int HEADER_TYPE_MAIN = 0;

    /**
     * 部门负责人类型 副负责人
     */
    int HEADER_TYPE_VICE = 1;
}

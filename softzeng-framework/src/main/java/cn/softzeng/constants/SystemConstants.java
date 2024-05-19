package cn.softzeng.constants;

import io.swagger.models.auth.In;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";

    public static final String  STATUS_NORMAL = "0";

    /***
     * 友链状态为审核通过
     */

    public static final String  LINK_STATUS_NORMAL = "0";
    /***
     * 评论类型为:文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /***
     * 评论类型为:友链评论
     */
    public static final String LINK_COMMENT ="1" ;
    public static final String MENU="C" ;
    public static final String BUTTON ="F" ;
    /***
     * 用户类型 1为后台用户
     */
    public static final String ADMAIN = "1";
    /***
     * 新增用户时，查询角色列表
     */
    public static final String NORMAL = "0";
}
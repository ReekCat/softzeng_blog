package cn.softzeng.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色信息表(SysRole)表实体类
 *
 * @author makejava
 * @since 2024-03-20 17:18:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class SysRole  {
/*
    //角色ID@TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
    //创建者
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新者
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
*/
    //角色ID@TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;

    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //备注
    private String remark;

    //关联菜单id数组，不是表中的字段  用来接收参数使用
    @TableField(exist = false)
    private Long[] menuIds;


}

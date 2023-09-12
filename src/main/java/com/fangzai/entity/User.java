package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id; // ID
    private String loginId; //用户账号
    private String password; //登录密码
    private String userName;  //用户名字
    private String userTelephone;  //用户电话
    private String unit;  //用户所属单位
    private String department;  //用户所属部门
    private String userPermission;  //用户权限
    private String accountStatus;  //账号状态
    private String roleId; //角色id
    private String unitId; //单位id
    private String departmentId; //部门id
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}


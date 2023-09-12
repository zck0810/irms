package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 赵长开
 * @description: 角色权限
 * @date 2023/9/6 14:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private String id; // ID
    private String insertUserId; //用户id
    private String state; //权限状态
    private String name;  //权限级别名称
}

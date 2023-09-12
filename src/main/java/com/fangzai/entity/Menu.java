package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 赵长开
 * @description: 菜单
 * @date 2023/9/6 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private String id; // ID
    private String state; //一级菜单权限状态
    private String menuName;  //一级菜单名称
    private Integer orderNum;  //排序
    private String menuIcon;  //图标
    private String path;  //对应字段
}

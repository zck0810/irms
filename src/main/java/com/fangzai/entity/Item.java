package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 赵长开
 * @description: 二级菜单
 * @date 2023/9/6 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String id; // ID
    private String state; //二级菜单权限状态
    private String name;  //二级菜单名称
    private Integer orderNum;  //排序
    private String url;
    private String path;
}

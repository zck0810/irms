package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author V5050E
 * @description:
 * @date 2023/9/7 9:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRel {
    private String id; // ID
    private String itemId; //二级菜单id
    private String menuId; //一级菜单id
    private String state; //一级菜单权限状态
}

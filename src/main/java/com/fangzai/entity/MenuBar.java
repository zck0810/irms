package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author V5050E
 * description: 路由数据
 * date 2023/9/7 11:08
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuBar extends MenuItemRel{
    private static final long serialVersionUID = 11213131212L;
    private String menuName; //一级菜单名称
    private String itemName; //二级菜单名称
    private Integer menuNum; //一级菜单序号（即第几个一级目录）
    private String menuIcon; //一级菜单图标
    private Integer itemNum; //二级菜单序号（即第几个二级目录）
    private String url ; //一级菜单序号（即第几个一级目录）
    private String path; //一级菜单路径（一级菜单的vue文件名）
    private String itemPath; //二级菜单路径（二级菜单的vue文件名）
    private boolean Check;
    public void setRoleItemRel(MenuItemRel menuItemRel){
        this.setMenuId(menuItemRel.getMenuId());
        this.setItemId(menuItemRel.getItemId());
        this.setId(menuItemRel.getId());
    }
}

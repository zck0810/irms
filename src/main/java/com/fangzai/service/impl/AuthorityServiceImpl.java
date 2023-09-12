package com.fangzai.service.impl;

import com.fangzai.entity.*;
import com.fangzai.entity.Menu;
import com.fangzai.mapper.RoleItemRelMapper;
import com.fangzai.mapper.ItemMapper;
import com.fangzai.mapper.MenuItemRelMapper;
import com.fangzai.mapper.MenuMapper;
import com.fangzai.service.AuthorityService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.List;

/**
 * @author 赵长开
 * description:
 * date  2023/9/6 16:40
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private ItemMapper itemMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private MenuItemRelMapper menuItemRelMapper;

    @Resource
    private RoleItemRelMapper roleItemRelMapper;
    @Override
    public List<MenuBar> getRoleAuthorityByRoleid(String roleId) {
        // 从数据库获取所有激活的Item、Menu、MenuItemRel、RoleItemRel列表
        List<Item> itemList = itemMapper.getAllItemActive();  // 获取所有激活的(二级菜单)Item列表
        List<Menu> menuList = menuMapper.getAllMenuActive();  // 获取所有激活的（一级菜单）Menu列表
        List<MenuItemRel> menuItemRelList = menuItemRelMapper.getAllMenuItemRelActive();  // 通过MenuItemRel表和item表进行连表查询，查询出需要的路由菜单字段
        List<RoleItemRel> roleItemRelList = roleItemRelMapper.getRoleItemRelListByRoleId(roleId);  // 根据roleId（角色权限id）获取相关的RoleItemRel(菜单栏数据)列表

        List<MenuBar> menuBarDataList = new ArrayList<>(); // 创建一个用于存储菜单栏结果的列表

        //根据一级菜单id和二级菜单id把把一二级菜单相关属性加到菜单栏中
        for (MenuItemRel menuItemRel : menuItemRelList) {
            MenuBar menuBar = new MenuBar();  // 创建一个新的MenuBar对象
            menuBar.setCheck(false);  // 设置check属性为false，表示当前角色不能看到这一条菜单栏数据，
            menuBar.setRoleItemRel(menuItemRel);  // 把一二级菜单id加到菜单栏中对象中

            //把二级菜单的相关属性加到菜单栏中
            for (Item item : itemList) {
                if (menuBar.getItemId().equals(item.getId())) {  // 比较menuBar的itemId和Item的id是否相等 如果相等，将Item的相关属性赋值给menuBar
                    menuBar.setItemName(item.getName()); //把二级菜单名称加到菜单栏中
                    menuBar.setUrl(item.getUrl()); //把一级菜单序号加到菜单栏中
                    menuBar.setItemNum(item.getOrderNum()); //把二级菜单序号加到菜单栏中
                    menuBar.setItemPath(item.getPath()); //把二级菜单文件名称（即组件名）加到菜单栏中，作为路由跳转路径
                    break;  // 停止遍历，因为已经找到匹配项
                }
            }

            //把一级菜单相关属性加到菜单栏中
            for (Menu menu : menuList) {
                if (menuBar.getMenuId().equals(menu.getId())) {  // 比较MenuItemRel的menuId和Menu的id是否相等 如果相等，将Menu的相关属性赋值给menuBar
                    menuBar.setMenuName(menu.getMenuName()); //把一级菜单名称加到菜单栏中
                    menuBar.setMenuNum(menu.getOrderNum());  //把一级菜单序号加到菜单栏中
                    menuBar.setMenuIcon(menu.getMenuIcon()); //把一级菜单的图标加到菜单栏中
                    menuBar.setPath(menu.getPath());  //把一级菜单文件名称（文件名）加到菜单栏中，作为路由跳转路径
                    break;  // 停止遍历，因为已经找到匹配项
                }
            }

            //判断角色是否能看到二级菜单项，如果能，把这一条菜单栏数据setCheck设置为true，表示该角色能看到这一条菜单栏数据
            for (RoleItemRel roleItemRel : roleItemRelList) {
                if (menuBar.getItemId().equals(roleItemRel.getItemId())) {  // 比较MenuItemRel的itemId和RoleItemRel的itemId是否相等
                    menuBar.setCheck(true);  // 如果相等，将check属性设为true
                    menuBarDataList.add(menuBar);// 将menuBar添加到menuBarDataList中
                    break;  // 停止遍历，因为已经找到匹配项
                }
            }
            // 根据ItemNum和MenuNum对menuBarDataList进行排序
            menuBarDataList.sort(Comparator.comparing(MenuBar::getItemNum));  // 先根据ItemNum排序
            menuBarDataList.sort(Comparator.comparing(MenuBar::getMenuNum));  // 在根据MenuNum排序
        }

        return menuBarDataList; // 返回结果列表
    }


}

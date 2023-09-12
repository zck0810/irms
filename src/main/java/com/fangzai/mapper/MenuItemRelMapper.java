package com.fangzai.mapper;

import com.fangzai.entity.MenuItemRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuItemRelMapper {

    @Select("select r.id, r.itemId, r.menuId, r.state, i.name, i.orderNum, i.url, i.path from MenuItemRel r, Item i where (r.state = '0') and (r.itemId = i.id) order by i.orderNum")
    List<MenuItemRel> getAllMenuItemRelActive();

}

package com.fangzai.mapper;

import com.fangzai.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select * from Menu where state = '0' order by orderNum")
    List<Menu> getAllMenuActive();
}

package com.fangzai.mapper;

import com.fangzai.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("select * from Item where state = '0' order by orderNum")
    List<Item> getAllItemActive();
}

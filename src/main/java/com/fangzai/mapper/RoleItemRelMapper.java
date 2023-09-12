package com.fangzai.mapper;

import com.fangzai.entity.RoleItemRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleItemRelMapper {

    @Select("select * from RoleItemRel where roleId = #{roleId}")
    List<RoleItemRel> getRoleItemRelListByRoleId(String roleId);
}

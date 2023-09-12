package com.fangzai.mapper;

import com.fangzai.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> list();


    @Insert("insert into user (id, loginId, password, userName, userTelephone, unit, department, userPermission, accountStatus, createTime, updateTime, roleId)" +
            "values (#{id},#{loginId},#{password},#{userName},#{userTelephone},#{unit},#{department},#{userPermission},#{accountStatus},#{createTime},#{createTime},#{roleId})")
    void insert(User user);

    @Select("select * from user where loginId = #{loginId} and password = #{password}")
    User getByLoginIdAndPassword(User user);
}

package com.fangzai.service;

import com.fangzai.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<User> list();

    void add(User user);

    User login(User user);
}

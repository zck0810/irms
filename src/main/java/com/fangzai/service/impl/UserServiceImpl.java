package com.fangzai.service.impl;
import com.fangzai.entity.User;
import com.fangzai.mapper.UserMapper;
import com.fangzai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public User login(User user) {
        return userMapper.getByLoginIdAndPassword(user);
    }
}

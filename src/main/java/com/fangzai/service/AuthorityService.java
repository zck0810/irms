package com.fangzai.service;

import com.fangzai.entity.MenuBar;

import java.util.List;

public interface AuthorityService {

    List<MenuBar> getRoleAuthorityByRoleid(String id);
}

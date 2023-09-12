package com.fangzai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 赵长开
 * @description:
 * @date 2023/9/6 14:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleItemRel {
    private String id; // ID
    private String roleId; //角色id
    private String itemId; //二级菜单id
}

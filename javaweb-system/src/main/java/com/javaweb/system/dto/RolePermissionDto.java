package com.javaweb.system.dto;

import lombok.Data;

/**
 * 角色权限设置
 */
@Data
public class RolePermissionDto {

    /**
     * 角色ID
     */
    Integer roleId;

    /**
     * 菜单ID集合
     */
    String[] menuIds;

}

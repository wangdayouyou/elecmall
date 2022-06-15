package com.sdxx.security.service.impl;

import com.sdxx.security.dao.PermissionMapper;
import com.sdxx.security.entity.Permission;
import com.sdxx.security.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionMapper permissionMapper;

    @Override
    public Permission queryById(Long id) {
        return this.permissionMapper.selectById(id);
    }

    @Override
    public List<Permission> queryAllByLimit(int offset, int limit) {
        return this.permissionMapper.queryAllByLimit(offset, limit);
    }

    @Override
    public Permission insert(Permission permission) {
        this.permissionMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return permission;
    }

    @Override
    public boolean deleteById(Long id) {
        return permissionMapper.deleteById(id) > 0;
    }
}

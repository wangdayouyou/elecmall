package com.sdxx.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdxx.security.dao.UserMapper;
import com.sdxx.security.entity.ElecUser;
import com.sdxx.security.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,ElecUser> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public ElecUser queryById(Long id) {
        return this.userMapper.selectById(id);
    }

    @Override
    public List<ElecUser> queryAllByLimit(int offset, int limit) {
        return this.userMapper.queryAllByLimit(offset,limit);
    }

    @Override
    public ElecUser insert(ElecUser elecUser) {
        userMapper.insert(elecUser);
        return elecUser;
    }

    @Override
    public ElecUser update(ElecUser elecUser) {
        userMapper.updateById(elecUser);
        return elecUser;
    }

    @Override
    public boolean deleteById(ElecUser elecUser) {
        return this.userMapper.deleteById(elecUser) > 0;
    }

    @Override
    public ElecUser selectByName(String userName) {
        return this.userMapper.selectByName(userName);
    }
}

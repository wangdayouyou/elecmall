package com.sdxx.security.service.impl;

import com.sdxx.security.dao.UserMapper;
import com.sdxx.security.entity.User;
import com.sdxx.security.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User queryById(Long id) {
        return this.userMapper.selectById(id);
    }

    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userMapper.queryAllByLimit(offset,limit);
    }

    @Override
    public User insert(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.updateById(user);
        return user;
    }

    @Override
    public boolean deleteById(User user) {
        return this.userMapper.deleteById(user) > 0;
    }

    @Override
    public User selectByName(String userName) {
        return this.userMapper.selectByName(userName);
    }
}

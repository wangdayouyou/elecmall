package com.sdxx.security.service;

import com.sdxx.security.entity.User;

import java.util.List;

/**
 * @author wd
 *
 * @description user服务接口
 */
public interface UserService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userAccount 实例对象
     * @return 实例对象
     */
    User insert(User userAccount);

    /**
     * 修改数据
     *
     * @param userAccount 实例对象
     * @return 实例对象
     */
    User update(User userAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(User id);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    User selectByName(String userName);
}

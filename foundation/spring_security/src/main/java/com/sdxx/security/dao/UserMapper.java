package com.sdxx.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sdxx.security.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据姓名查询
     * @param userName
     * @return
     */
    User selectByName(String userName);
}

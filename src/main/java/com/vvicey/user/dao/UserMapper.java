package com.vvicey.user.dao;

import com.vvicey.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("userMapper")
public interface serMapper {

    int deleteByPrimaryKey(Integer uid);

    int insertSelective(User record);

    User selectUser(@Param("username") String username,@Param("password") String password);

    int updateByPrimaryKeySelective(User record);
}
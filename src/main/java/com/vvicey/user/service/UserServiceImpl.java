package com.vvicey.user.service;

import com.vvicey.user.dao.UserMapper;
import com.vvicey.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int createUser(User user) {
        int s = userMapper.insertSelective(user);
        return s;
    }

    @Override
    public int cutOutUser(Integer uid) {
        int s = userMapper.deleteByPrimaryKey(uid);
        return s;
    }

    @Override
    public int reviseUser(User record) {
        int s = userMapper.updateByPrimaryKeySelective(record);
        return s;
    }

    @Override
    public User checkUser(String username, String password) {
        User theUser = userMapper.selectUser(username, password);
        return theUser;
    }
}

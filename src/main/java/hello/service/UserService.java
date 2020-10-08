package hello.service;

import hello.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;
    private SqlSession sqlSession;

    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer id){
        User user=userMapper.findUserById(id);
        return user;
    }
}

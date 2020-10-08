package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private UserMapper userMapper;

    private Map<String, User> users = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        save("asd","asd123");
    }

    public void save(String username, String password) {
        users.put(username,new User(1,username,bCryptPasswordEncoder.encode(password)));
    }
//    public String getPassword(String username){
//        return userPassword.get(username).getEncryptedPassword();
//    }
//    @Autowired
//    public UserService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }

//    public User getUserById(Integer id) {
////        User user=userMapper.findUserById(id);
//        return userMapper.findUserById(id);

    public User getUserByUsername(String username){
        return users.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username)){
            throw new UsernameNotFoundException(username+"不存在");
        }
        String encryptedPassword = users.get(username).getEncryptedPassword();
        return new org.springframework.security.core.userdetails.User(username,encryptedPassword, Collections.emptyList());
    }
}

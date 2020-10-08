package hello.service;

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

    private Map<String, String> userPassword = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(String username, String password) {
        userPassword.put(username,bCryptPasswordEncoder.encode(password));
    }
    public String getPassword(String username){
        return userPassword.get(username);
    }
//    @Autowired
//    public UserService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }

//    public User getUserById(Integer id) {
////        User user=userMapper.findUserById(id);
//        return userMapper.findUserById(id);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userPassword.containsKey(username)){
            throw new UsernameNotFoundException(username+"不存在");
        }
        String encodepassword = userPassword.get(username);

        return new org.springframework.security.core.userdetails.User(username,encodepassword, Collections.emptyList());
    }
}

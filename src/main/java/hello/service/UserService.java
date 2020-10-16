package hello.service;

import hello.entity.User;
import hello.dao.UserMapper;
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
    private UserMapper userMapper;

    private Map<String, User> users = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(String username, String password) {
        userMapper.save(username, bCryptPasswordEncoder.encode(password));
    }

    public User getUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }
        String encryptedPassword = user.getEncryptedPassword();
        return new org.springframework.security.core.userdetails.User(username, encryptedPassword, Collections.emptyList());
    }

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}

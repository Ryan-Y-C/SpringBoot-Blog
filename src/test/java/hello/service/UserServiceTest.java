package hello.service;

import hello.entity.User;
import hello.dao.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;
    @InjectMocks
    UserService userService;
    @Test
    public void testSave(){
        //调用userService
        //验证userService将请求转发给了userMapper

        when(mockEncoder.encode("myPassword")).thenReturn("myEncodedPassword");

        userService.save("myUsername","myPassword");

        verify(mockMapper).save("myUsername","myEncodedPassword");
    }
    @Test
    public void testGetUserByUsername(){
        userService.getUserByUsername("myUsername");
        verify(mockMapper).findUserByUsername("myUsername");
    }
    @Test
    public void  throwExceptionWhenUserNotFound(){
        when(mockMapper.findUserByUsername("myUsername")).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class,()->userService.loadUserByUsername("myUsername"));
    }
    @Test
    public void  returnUserDetailsWhenUserFound(){
        when(mockMapper.findUserByUsername("myUsername")).thenReturn(new User("myUsername","myEncryptedPassword"));
        UserDetails userdetails = userService.loadUserByUsername("myUsername");
        Assertions.assertEquals("myUsername",userdetails.getUsername());
        Assertions.assertEquals("myEncryptedPassword",userdetails.getPassword());
    }

}
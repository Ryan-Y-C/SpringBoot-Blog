package hello.config;

import hello.mapper.UserMapper;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class JavaConfiguration {
//    @Bean
//    public UserService userService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper){
//        return new UserService(bCryptPasswordEncoder,userMapper);
//    }
}

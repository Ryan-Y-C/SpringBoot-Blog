package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    @Mock
    UserService mockService;
    @Mock
    AuthenticationManager mockManager;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        //声明要测试的类
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(mockService, mockManager)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("true")));
    }

    @Test
    void testLogin() throws Exception {
        //未登录状态
        mvc.perform(get("/auth")).andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("false")));

        Map<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put("username", "userName");
        usernamePassword.put("password", "passWord");

        Mockito.when(mockService.loadUserByUsername("userName")).thenReturn(new User("userName", bCryptPasswordEncoder.encode("passWord"), Collections.emptyList()));
        Mockito.when(mockService.getUserByUsername("userName")).thenReturn(new hello.entity.User("userName", bCryptPasswordEncoder.encode("passWord")));


        MvcResult response = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(usernamePassword)))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    mvcResult.getResponse().setCharacterEncoding("UTF-8");
                    Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("登录成功"));
                })
                .andReturn();
        HttpSession session = response.getRequest().getSession();

        //登录状态
        mvc.perform(get("/auth")
                .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("userName")));

    }
}
package hello.service;

import hello.dao.BlogDao;
import hello.entity.BlogResult;
import hello.entity.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogFormDb() {
        blogService.getBlog(1, 10, null);
        verify(blogDao).getBlog(1, 10, null);
    }
    @Test
    public void returnFailureWhenExceptionThrown(){
        when(blogDao.getBlog(anyInt(),anyInt(),any())).thenThrow( new RuntimeException());
        Status result = blogService.getBlog(1, 10, null);
        Assertions.assertEquals("fail",result.getStatus());
        Assertions.assertEquals("系统异常",result.getMsg());

    }
}

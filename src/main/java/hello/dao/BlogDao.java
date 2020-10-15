package hello.dao;

import hello.entity.Blog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogDao {
    public List<Blog> getBlog(Integer page, Integer pageSize, Integer userId) {
        return null;
    }

    public Object getBlog(int anyInt) {
        return null;
    }
    public int count(Integer user){
        return 0;
    }
}

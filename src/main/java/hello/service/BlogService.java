package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogResult;
import hello.entity.Status;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {

    BlogDao blogDao;
    UserService userService;

    @Inject
    public BlogService(BlogDao blogDao,UserService userService) {
        this.blogDao = blogDao;
        this.userService=userService;
    }

    public Status getBlog(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlog(page, pageSize, userId);
            blogs.forEach(blog->blog.setUser(userService.getUserById(blog.getUserId())));
            int count = blogDao.count(userId);
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogResult.newBlogs(blogs,count,page,pageCount);
        }catch (RuntimeException e){
//            throw new RuntimeException(e);
            return Status.failStatus("系统异常");
        }
    }
}

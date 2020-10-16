package hello.dao;

import hello.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlog(Integer page, Integer pageSize, Integer userId) {
        Map<String,Object> param=new HashMap<>();
        param.put("user_id",userId);
        param.put("offset",(page-1)*pageSize);
        param.put("limit",pageSize);
        return sqlSession.selectList("selectBlog",param);
    }

    public Object getBlog(int anyInt) {
        return null;
    }
    public int count(Integer user){
        Map<String,Object> param=new HashMap<>();
        param.put("user_id",user);
        return sqlSession.selectOne("countBlog",param);
    }
}

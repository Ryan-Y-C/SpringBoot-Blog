package hello.mapper;

import hello.service.OrderService;
import hello.service.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findUserById(@Param("id") Integer id);

    OrderService findOrderService();


}

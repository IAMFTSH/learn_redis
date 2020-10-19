package learn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 邝明山
 * on 2020/10/18 16:14
 */
@SpringBootTest
public class TestString {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        //测试数字类型的操作,不能使用双引号
        redisTemplate.opsForValue().set("int",1);
        //+1操作
        redisTemplate.opsForValue().increment("int");
        //-1操作
        redisTemplate.opsForValue().decrement("int");

        redisTemplate.opsForValue().set("set","0123456789");
        System.out.println(redisTemplate.opsForValue().getAndSet("set","getAndSet"));

    }

}

package learn.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    void redisTemplateTest(){
        redisTemplate.opsForValue().set("JAVA","JavaValue");
        System.out.println(redisTemplate.opsForValue().get("JAVA"));
        redisTemplate.opsForHash().put("javaHash","hash1","123");
        System.out.println(redisTemplate.opsForHash().get("javaHash","hash1"));
    }
    //如果redisTemplate没有序列化，就需要变成字符串存储
    @Test
    void setUser() throws JsonProcessingException {
        User user=new User("jay",18);
        String userJson=new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",userJson);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    //如果redisTemplate使用了json，就可以直接用对象储存
    @Test
    void setUserSer() throws JsonProcessingException {
        User user=new User("jay",18);
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}

package learn.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.transaction.annotation.Transactional;

//真的的测试，其他的是api调用测试
@SpringBootTest
class RedisApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
    }

    //测试连接上限（没有测出来）
    @Test
    public void testManyOperations(){
        redisTemplate.opsForValue().set("test1","1");
        redisTemplate.opsForValue().set("test2","2");
        redisTemplate.opsForValue().set("test3","3");
        redisTemplate.opsForValue().set("test4","4");
        redisTemplate.opsForValue().set("test5","5");
        redisTemplate.opsForValue().set("test6","6");
        redisTemplate.opsForValue().set("test7","7");
        redisTemplate.opsForValue().set("test8","8");
        redisTemplate.opsForValue().set("test9","9");
        redisTemplate.opsForValue().set("test10","10");
        redisTemplate.opsForValue().set("test11","11");
        redisTemplate.opsForValue().set("test12","12");
        redisTemplate.opsForValue().set("est1","1");
        redisTemplate.opsForValue().set("est2","2");
        redisTemplate.opsForValue().set("est3","3");
        redisTemplate.opsForValue().set("est4","4");
        redisTemplate.opsForValue().set("est5","5");
        redisTemplate.opsForValue().set("est6","6");
        redisTemplate.opsForValue().set("est7","7");
        redisTemplate.opsForValue().set("est8","8");
        redisTemplate.opsForValue().set("est9","9");
        redisTemplate.opsForValue().set("est10","10");
        redisTemplate.opsForValue().set("est11","11");
        redisTemplate.opsForValue().set("est12","12");
        redisTemplate.opsForValue().set("st1","1");
        redisTemplate.opsForValue().set("st2","2");
        redisTemplate.opsForValue().set("st3","3");
        redisTemplate.opsForValue().set("st4","4");
        redisTemplate.opsForValue().set("st5","5");
        redisTemplate.opsForValue().set("st6","6");
        redisTemplate.opsForValue().set("st7","7");
        redisTemplate.opsForValue().set("st8","8");
        redisTemplate.opsForValue().set("st9","9");
        redisTemplate.opsForValue().set("st10","10");
        redisTemplate.opsForValue().set("st11","11");
        redisTemplate.opsForValue().set("st12","12");
    }


    @Test
    void redisTemplateTest(){
        redisTemplate.opsForValue().set("JAVA","JavaValue");
        System.out.println(redisTemplate.opsForValue().get("JAVA"));
        redisTemplate.opsForHash().put("javaHash","hash1","321");
        redisTemplate.opsForHash().entries("javaHash").put("hash1","321");
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

    //如果redisTemplate使用了json序列化，就可以直接用对象储存
    @Test
    void setUserSer() throws JsonProcessingException {
        User user=new User("jay",18);
        redisTemplate.opsForValue().set("user",user);
        User user1=(User )redisTemplate.opsForValue().get("user");
        System.out.println(user1.getName());
    }

    //一个链接里面做多个redis命令,但是不支持事务,不保持原子性，需要加上@Transactional才行
    //RedisCallback需要实现底层，比较复杂
    @Test
    @Transactional
    public void useRedisCallback(){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.opsForValue().set("test1","1");
                operations.opsForValue().set("test2","2");
                operations.opsForValue().set("test3","3");
                operations.opsForValue().set("test4","4");
                operations.opsForValue().set("test5","5");
                operations.opsForValue().set("test6","6");
                operations.opsForValue().set("test7","7");
                System.out.println(1/0);
                operations.opsForValue().set("test8","8");
                operations.opsForValue().set("test9","9");
                operations.opsForValue().set("test10","10");
                operations.opsForValue().set("test11","11");
                operations.opsForValue().set("test12","12");
                return null;
            }
        });
    }
}

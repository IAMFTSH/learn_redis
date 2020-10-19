package learn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author 邝明山
 * on 2020/10/18 20:43
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void Test(){
        HashMap hashMap=new HashMap();
        hashMap.put("redisHash",1);
        hashMap.put("redisHash1",2);
        hashMap.put("redisHash2",3);
        hashMap.put("redisHash3",4);
        hashMap.put("int",0);
        redisTemplate.opsForHash().putAll("hashMap",hashMap);
        //判断是否存在
        System.out.println(redisTemplate.hasKey(""));
        //设置过期时间
        redisTemplate.expire("hashMap", Duration.ofSeconds(5));
        System.out.println(redisTemplate.getClientList());
        //随机取一个值
        System.out.println(redisTemplate.randomKey());
        System.out.println(redisTemplate.getExpire("hashMap"));
        System.out.println(redisTemplate.getDefaultSerializer());
        System.out.println(redisTemplate.isExposeConnection());
        //移动数据到数据库1（从0开始）
        System.out.println(redisTemplate.move("asd",1));
        //模糊匹配
        System.out.println(redisTemplate.keys("hash*"));
        //将当前传入的key值序列化为byte[]类型,序列号给定的值
        System.out.println(redisTemplate.dump("321"));
        //移除 key 的过期时间，key 将持久保持
        System.out.println(redisTemplate.persist("hashMap"));
        //不知道干嘛
        //System.out.println(redisTemplate.sort());

    }

    //绑定操作
    @Test
    void  TestBound(){
        HashMap hashMap=new HashMap();
        hashMap.put("redisHash",1);
        hashMap.put("redisHash1",2);
        hashMap.put("redisHash2",3);
        hashMap.put("redisHash3",4);
        hashMap.put("int",0);
        redisTemplate.opsForHash().putAll("hashMap",hashMap);
        redisTemplate.expire("hashMap", Duration.ofSeconds(5));
        //绑定hashmap操作
        BoundHashOperations boundHashOps = redisTemplate.boundHashOps("hashMap");
        System.out.println(boundHashOps.get("redisHash"));
        System.out.println(boundHashOps.getExpire());
    }
}

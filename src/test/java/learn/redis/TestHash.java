package learn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 邝明山
 * on 2020/10/18 16:14
 */
@SpringBootTest
public class TestHash {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void  TestHash(){
        HashMap hashMap=new HashMap();
        hashMap.put("redisHash",1);
        hashMap.put("redisHash1",2);
        hashMap.put("redisHash2",3);
        hashMap.put("redisHash3",4);
        hashMap.put("int",0);
        redisTemplate.opsForHash().putAll("hashMap",hashMap);
        redisTemplate.opsForHash().put("hashMap","redisHash4",5);
        LinkedHashMap linkedHashMap=(LinkedHashMap)redisTemplate.opsForHash().entries("hashMap");
        System.out.println(linkedHashMap.getClass()+":"+linkedHashMap);
        ArrayList arrayList=(ArrayList) redisTemplate.opsForHash().values("hashMap");
        System.out.println(arrayList.getClass()+":"+arrayList);
        System.out.println(redisTemplate.opsForHash().keys("hashMap"));
        System.out.println(redisTemplate.opsForHash().hasKey("hashMap","redisHash"));
        System.out.println(redisTemplate.opsForHash().size("hashMap"));
        //而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
        System.out.println(redisTemplate.opsForHash().putIfAbsent("hashMap","int","19"));
        System.out.println(redisTemplate.opsForHash().increment("hashMap","int",3));
        System.out.println(redisTemplate.opsForHash().increment("hashMap","int",3.9));
        System.out.println(redisTemplate.opsForHash().lengthOfValue("hashMap","int"));
        //匹配获取键值对，ScanOptions.NONE为获取全部键对，ScanOptions.scanOptions().match("map1").build()     匹配获取键位map1的键值对,不能模糊匹配 下面只匹配了redisHash1
        //Map.Entry是为了更方便的输出map键值对。一般情况下，要输出Map中的key 和 value  是先得到key的集合keySet()，然后再迭代（循环）由每个key得到每个value。values()方法是获取集合中的所有值，不包含键，没有对应关系。而Entry可以一次性获得这两个值
        Cursor<Map.Entry<Object,Object>> cursor =redisTemplate.opsForHash().scan("hashMap", ScanOptions.scanOptions().match("redisHash3").match("redisHash1").build());
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            System.out.println("通过scan(H key, ScanOptions options)方法获取匹配键值对:" + entry.getKey() + "---->" + entry.getValue());
        }
    }


}

package learn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author 邝明山
 * on 2020/10/18 22:27
 */
@SpringBootTest
public class TestSet {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void TestSet(){
        SetOperations opsForSet = redisTemplate.opsForSet();

        opsForSet.add("redisSet",1,2,3,4,5,6);
        opsForSet.add("setMove","不能为空");
        System.out.println(opsForSet.members("redisSet"));
        //pop随机出4个元素
        System.out.println(opsForSet.pop("redisSet",4));
        System.out.println(opsForSet.members("redisSet"));
        //随机获得一个成员
        System.out.println(opsForSet.randomMember("redisSet"));
        System.out.println(opsForSet.isMember("redisSet",5));
        System.out.println(opsForSet.move("redisSet","3","setMove"));

        opsForSet.add("A",1,2,3,4,5,6);
        opsForSet.add("B",3,4,5,6,7);
        //求差值 结果1和2没有7
        System.out.println(opsForSet.difference("A","B"));
        //将求出来的差值元素保存
        System.out.println(opsForSet.differenceAndStore("A","B","C"));
        System.out.println(opsForSet.remove("A",1,2,3,4));
        //获取去重的随机元素。
        System.out.println(opsForSet.distinctRandomMembers("B",2));
        opsForSet.add("a",1,2,3,4,5,6);
        opsForSet.add("b",3,4,5,6,7);
        System.out.println(opsForSet.intersect("a","b"));
        //将求出来的交集元素保存,返回元素数目
        System.out.println(opsForSet.intersectAndStore("a","b","c"));
        System.out.println(opsForSet.union("a","b"));
        //将求出来的合集元素保存,返回元素数目
        System.out.println(opsForSet.unionAndStore("a","b","c"));


    }
}

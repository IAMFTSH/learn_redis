package learn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 邝明山
 * on 2020/10/19 8:25
 */
@SpringBootTest
public class TestZSet {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void testZSet(){
        ZSetOperations opsForZSet = redisTemplate.opsForZSet();
        opsForZSet.add("zSet","zSet1",1);
        opsForZSet.add("zSet","zSet2",2);
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = new HashSet<ZSetOperations.TypedTuple<Object>>();
        ZSetOperations.TypedTuple typedTupleA=new DefaultTypedTuple<Object>("A",4.0);
        ZSetOperations.TypedTuple typedTupleB=new DefaultTypedTuple<Object>("B",5.3);
        ZSetOperations.TypedTuple typedTupleC=new DefaultTypedTuple<Object>("C",3.3);
        typedTupleSet.add(typedTupleA);
        typedTupleSet.add(typedTupleB);
        typedTupleSet.add(typedTupleC);
        opsForZSet.add("zSet", typedTupleSet);
        //获得zSet 2-4的score
        System.out.println(opsForZSet.rangeByScore("zSet",2,4));
        //根据下标2-3获得TypedTuple,下标开始位置为0
        Set<DefaultTypedTuple<Object>> zSet = opsForZSet.rangeWithScores("zSet", 2, 3);
        for (DefaultTypedTuple<Object> typedTuple:zSet) {
            System.out.println(typedTuple.getValue()+":"+typedTuple.getScore());
        }
        //获取score区间内元素个数
        System.out.println(opsForZSet.count("zSet",2,4));
        //获取变量中元素的索引,下标开始位置为0
        System.out.println(opsForZSet.rank("zSet","A"));
        //   获取元素的分值
        System.out.println(opsForZSet.score("zSet","A"));
        //获取变量中元素的个数
        System.out.println(opsForZSet.zCard("zSet"));
        //修改变量中的元素的分值，这里是+，不是直接变成199
        System.out.println(opsForZSet.incrementScore("zSet","A",199));
        //索引倒序排列指定区间元素  0下标变成size-1.其他关于reverse估计也是这样
        System.out.println(opsForZSet.reverseRange("zSet",2,4));

        opsForZSet.add("zA","1",1);
        opsForZSet.add("zA","2",2);
        opsForZSet.add("zA","3",3);
        opsForZSet.add("zB","1",1);
        opsForZSet.add("zB","2",2);
        opsForZSet.add("zB","3",3);
        //获取2个变量的交集存放到第3个变量里面,分数直接叠加
        System.out.println(opsForZSet.intersectAndStore("zA","zB","zC"));
        //获取2个变量的合集存放到第3个变量里面,分数直接叠加
        System.out.println(opsForZSet.unionAndStore("zA","zB","zD"));
    }
}

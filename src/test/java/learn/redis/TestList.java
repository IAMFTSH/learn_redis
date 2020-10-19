package learn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 邝明山
 * on 2020/10/18 16:14
 */
@SpringBootTest
public class TestList {
    @Autowired
    private RedisTemplate redisTemplate;


    //注意如果操作不存在的值会报错
    @Test
    void TestList(){
        ListOperations listOperations = redisTemplate.opsForList();
        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        System.out.println(list);
        //第一个是6
        listOperations.leftPushAll("redisList",list);
        //第一个是1
        //listOperations.rightPushAll("redisList",list);
        //从尾部出
        //System.out.println(listOperations.rightPop("redisList"));
        //从头部出
        //System.out.println(listOperations.leftPop("redisList"));
        //获得根据下标获得list的值
        System.out.println(listOperations.index("redisList",5));
        //仅当集合存在时，向集合中添加一个元素，从左到右
        System.out.println(listOperations.leftPushIfPresent("redisList","试试"));
        //timeout：连接超时时长  TimeUnit unit：超时时间单位
        System.out.println(listOperations.leftPop("redisList", 1,TimeUnit.SECONDS));
        //在原集合中删除最后一个元素，添加到目标几何中的头部（添加顺序是从左往右）  下面两个因为原集合和目标集合一致，相当于，最后元素变第一
        System.out.println(listOperations.rightPopAndLeftPush("redisList","redisList"));
        System.out.println(listOperations.range("redisList",1,4));
        //修剪数据，保留start到end的数据
        listOperations.trim("redisList",0,0);
        listOperations.set("redisList",0,"覆盖下标0的值");
        listOperations.rightPush("redisList","如果集合中没有值会删除集合");
        // 删除集合中的值，因为list中允许重复数据存在，所以可能有多个相同的   元素存在同一个集合中，这是可用count参数选择性删除数量
        listOperations.remove("redisList",1,"如果集合中没有值会删除集合");
    }
}

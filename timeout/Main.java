package com.macro.mall.tiny.timeout;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new RedisClient().show();
//        Jedis jedis = jedisPool.getResource();
//        System.out.println(jedis.del("key001"));
    }

}
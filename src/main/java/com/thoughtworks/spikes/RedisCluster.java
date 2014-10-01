package com.thoughtworks.spikes;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;

public class RedisCluster {

    private final JedisPool redisConnectionPool;

    public RedisCluster() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        // Tests whether connection is dead when connection
        // retrieval method is called
        poolConfig.setTestOnBorrow(true);
        /* Some extra configuration */
        // Tests whether connection is dead when returning a
        // connection to the pool
        poolConfig.setTestOnReturn(true);
        // Tests whether connections are dead during idle periods
        poolConfig.setTestWhileIdle(true);
        // Maximum number of connections to test in each idle check
        poolConfig.setNumTestsPerEvictionRun(10);
        // Idle connection checking period
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        // Create the jedisPool
        redisConnectionPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public Jedis getRedisClient() {
        return redisConnectionPool.getResource();
    }

    public void returnRedisClient(Jedis jedis) {
        redisConnectionPool.returnResource(jedis);
    }

}

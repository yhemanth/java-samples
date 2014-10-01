package com.thoughtworks.spikes.redis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class RedisQuery {

    public static final String KEY_PREFIX = "DATAMANT_";
    private RedisCluster redisCluster;
    private Long[] userIdSuffixes;

    public RedisQuery() {
        initializeRedisClient();
        initializeData();
    }

    private void initializeData() {
        userIdSuffixes = new Long[100];
        Random randUserIdGenerator = new Random();
        for (int i = 0; i < userIdSuffixes.length; i++) {
            userIdSuffixes[i] = randUserIdGenerator.nextLong();
        }
    }

    private void initializeRedisClient() {
        redisCluster = new RedisCluster();
    }

    private void runQueries() throws InterruptedException {
        Random randUserIdSelector = new Random();
        Random randBitValueGenerator = new Random();
        for (int i=0; i<100000; i++) {
            int index = randUserIdSelector.nextInt(userIdSuffixes.length);
            setBit(userIdSuffixes[index], randBitValueGenerator);
            getBitKeyValue(userIdSuffixes[index]);
            Thread.sleep(0, 1000);
        }
    }

    private void getBitKeyValue(long randSuffix) {
        Jedis redisClient = redisCluster.getRedisClient();
        String key = formKey(randSuffix);
        Boolean currentBit = redisClient.getbit(key, 1);
        System.out.println(String.format("Current %s:%s", key, currentBit));
        redisCluster.returnRedisClient(redisClient);
    }

    private void setBit(long randSuffix, Random randBitValueGenerator) {
        Jedis redisClient = redisCluster.getRedisClient();
        String key = formKey(randSuffix);
        boolean newBit = randBitValueGenerator.nextBoolean();
        Boolean oldBit = redisClient.setbit(key, 1, newBit);
        System.out.println(String.format("Old %s:%s->%s", key, oldBit, newBit));
        redisCluster.returnRedisClient(redisClient);
    }

    private String formKey(long randKey) {
        return KEY_PREFIX + randKey;
    }

    public static void main(String[] args) throws InterruptedException {
        RedisQuery redisQuery = new RedisQuery();
        redisQuery.runQueries();
    }

}

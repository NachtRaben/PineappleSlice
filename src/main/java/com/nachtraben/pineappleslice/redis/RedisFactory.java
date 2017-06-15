package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisFactory {

    private JedisPool pool;

    public RedisFactory(RedisProperties properties) {
        pool = new JedisPool(new JedisPoolConfig(), properties.getHost(), properties.getPort(), properties.getTimeout(), properties.getPassword());
    }

    public <T> T performOperation(JedisCallback<T> callback) {
        Jedis jedis = null;
        T result;
        try {
            jedis = pool.getResource();
            result = callback.call(jedis);
        } catch (JedisException e) {
            System.err.println("An error occurred while communicating with the database.");
            throw e;
        } finally {
            if(jedis != null)
                jedis.close();
        }
        return result;
    }

}

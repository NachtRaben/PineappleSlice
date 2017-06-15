package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public interface Redis extends AutoCloseable {

    void select(int index);

    int getIndex();

    /* Getters  and Setters */
    String get(String key);

    void set(String key, String value);

    String hget(String key, String hash);

    Map<String, String> hgetall(String key);

    void hset(String key, String hash, String value);

    List<String> hmget(String key, String... hashes);

    void hmset(String key, Map<String, String> hashes);

    List<String> scan();

    List<String> scan(String pattern);

    List<String> scan(String pattern, int count);

    List<Map.Entry<String, String>> hscan(String key);

    List<Map.Entry<String, String>> hscan(String key, String pattern);

    List<Map.Entry<String, String>> hscan(String key, String pattern, int count);

    /* Expirations */
    boolean expire(String key, int seconds);

    boolean expireat(String key, long time);

    boolean pexpire(String key, long ms);

    /* Deleters */
    Long del(String... keys);

    Long hdel(String key, String... hashes);

    Jedis getJedis();

}

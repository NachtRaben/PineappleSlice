package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Map;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public interface Redis extends AutoCloseable {

    /**
     * Selects a DB on the redis.
     *
     * @param index the index
     */
    void select(int index) throws JedisException;

    /**
     * Gets the current DB index.
     *
     * @return the index
     */
    int getIndex();

    /**
     * Get string value attached to a key.
     *
     * @param key the key
     *
     * @return the string
     */
/* Getters  and Setters */
    String get(String key) throws JedisException;

    /**
     * Sets a key to a value..
     *
     * @param key   the key
     * @param value the value
     */
    void set(String key, String value) throws JedisException;

    /**
     * Gets a JSON string of the key's value.
     *
     * @param key  the key
     * @param hash the hash
     *
     * @return the string
     */
    String hget(String key, String hash) throws JedisException;

    /**
     * Gets a map of all tokens and values in a key.
     *
     * @param key the key
     *
     * @return the map
     */
    Map<String, String> hgetall(String key) throws JedisException;

    /**
     * Sets a token and value under the specific key.
     *
     * @param key   the key
     * @param hash  the hash
     * @param value the value
     */
    void hset(String key, String hash, String value) throws JedisException;

    /**
     * Gets the values of all the hash tokens specified.
     *
     * @param key    the key
     * @param hashes the hashes
     *
     * @return the list
     */
    List<String> hmget(String key, String... hashes) throws JedisException;

    /**
     * Sets keys hashes and tokens specified in the hashmap
     *.
     *
     * @param key    the key
     * @param hashes the hashes
     */
    void hmset(String key, Map<String, String> hashes) throws JedisException;

    /**
     * Gets all keys in the currently selected database.
     *
     * @return the list
     */
    List<String> scan() throws JedisException;

    /**
     * Gets all the keys in the currently selected database based on a pattern.
     *
     * @param pattern the pattern
     *
     * @return the list
     */
    List<String> scan(String pattern) throws JedisException;

    /**
     * Gets all the keys in the currently selected database with pattern and limit.
     *
     * @param pattern the pattern
     * @param count   the count
     *
     * @return the list
     */
    List<String> scan(String pattern, int count) throws JedisException;

    /**
     * Gets a list of tokens and values for the specified key.
     *
     * @param key the key
     *
     * @return the list
     */
    List<Map.Entry<String, String>> hscan(String key) throws JedisException;

    /**
     * Gets a list of tokens and values for the specified key based on a pattern.
     *
     * @param key     the key
     * @param pattern the pattern
     *
     * @return the list
     */
    List<Map.Entry<String, String>> hscan(String key, String pattern) throws JedisException;

    /**
     * Gets a list of tokens and values for the specified key based on a pattern and limit.
     *
     * @param key     the key
     * @param pattern the pattern
     * @param count   the count
     *
     * @return the list
     */
    List<Map.Entry<String, String>> hscan(String key, String pattern, int count) throws JedisException;

    /**
     * Sets an expiration time in seconds for a specified key.
     * This will update prexisting values
     *
     * @param key     the key
     * @param seconds the seconds
     *
     * @return the boolean
     */
/* Expirations */
    boolean expire(String key, int seconds) throws JedisException;

    /**
     * Expireat boolean.
     *
     * @param key  the key
     * @param time the time
     *
     * @return the boolean
     */
    boolean expireat(String key, long time) throws JedisException;

    /**
     * Pexpire boolean.
     *
     * @param key the key
     * @param ms  the ms
     *
     * @return the boolean
     */
    boolean pexpire(String key, long ms) throws JedisException;

    /**
     * Del long.
     *
     * @param keys the keys
     *
     * @return the long
     */
/* Deleters */
    Long del(String... keys) throws JedisException;

    /**
     * Delete specified hashes from provided key.
     *
     * @param key    the key
     * @param hashes the hashes
     *
     * @return the long
     */
    Long hdel(String key, String... hashes) throws JedisException;

    /**
     * Gets jedis.
     *
     * @return the jedis
     */
    Jedis getJedis();

}

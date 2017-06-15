package com.nachtraben.pineappleslice.redis.impl;

import com.nachtraben.pineappleslice.redis.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by NachtRaben on 4/21/2017.
 */
public class RedisSessionImpl implements Redis {

    private static final Logger LOGGER = LoggerFactory.getLogger(Redis.class);

    private final Jedis connection;
    private int index;

    public RedisSessionImpl(Jedis connection, int index) {
        this.connection = connection;
        select(index);
    }

    @Override
    public void select(int index) {
        if(index < 0)
            throw new IllegalArgumentException("Indexes must be non-negative!");
        try {
            connection.select(index);
            this.index = index;
        } catch (JedisException e) {
            onError();
        }

    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String get(String key) {
        try {
            checkIndex();
            return connection.get(key);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public void set(String key, String value) {
        try {
            checkIndex();
            connection.set(key, value);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public String hget(String key, String hash) {
        try {
            checkIndex();
            return connection.hget(key, hash);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public Map<String, String> hgetall(String key) {
        try {
            checkIndex();
            return connection.hgetAll(key);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public void hset(String key, String hash, String value) {
        try {
            checkIndex();
            connection.hset(key, hash, value);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public List<String> hmget(String key, String... hashes) {
        try {
            checkIndex();
            return connection.hmget(key, hashes);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public void hmset(String key, Map<String, String> hashes) {
        try {
            checkIndex();
            connection.hmset(key, hashes);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public List<String> scan() {
        return scan("*");
    }

    @Override
    public List<String> scan(String pattern) {
        return scan(pattern, -1);
    }

    @Override
    public List<String> scan(String pattern, int count) {
        ScanParams params = new ScanParams().match(pattern);
        if(count != -1 && count > 0)
            params = params.count(count);

        List<String> keys = new ArrayList<>();
        try {
            checkIndex();
            ScanResult<String> result;
            String cursor = ScanParams.SCAN_POINTER_START;
            do {
                result = connection.scan(cursor, params);
                keys.addAll(result.getResult());
                cursor = result.getStringCursor();
            } while(!cursor.equals(ScanParams.SCAN_POINTER_START));
        } catch (JedisException e) {
            onError();
            throw e;
        }
        return keys;
    }

    @Override
    public List<Entry<String, String>> hscan(String key) {
        return hscan(key, "*", -1);
    }

    @Override
    public List<Entry<String, String>> hscan(String key, String pattern) {
        return hscan(key, pattern, -1);
    }

    @Override
    public List<Entry<String, String>> hscan(String key, String pattern, int count) {
        ScanParams params = new ScanParams().match(pattern);
        if(count != -1 && count > 0)
            params = params.count(count);

        List<Entry<String, String>> keys = new ArrayList<>();
        try {
            checkIndex();
            ScanResult<Entry<String, String>> result;
            String cursor = ScanParams.SCAN_POINTER_START;
            do {
                result = connection.hscan(key, cursor, params);
                keys.addAll(result.getResult());
                cursor = result.getStringCursor();
            } while(!cursor.equals(ScanParams.SCAN_POINTER_START));
        } catch (JedisException e) {
            onError();
            throw e;
        }
        return keys;
    }

    @Override
    public boolean expire(String key, int seconds) {
        try {
            checkIndex();
            return connection.expire(key, seconds) == 1;
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public boolean expireat(String key, long unixTime) {
        try {
            checkIndex();
            return connection.expireAt(key, unixTime) == 1;
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public boolean pexpire(String key, long ms) {
        try {
            checkIndex();
            return connection.pexpire(key, ms) == 1;
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public Long del(String... keys) {
        try {
            checkIndex();
            return connection.del(keys);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public Long hdel(String key, String... hashes) {
        try {
            checkIndex();
            return connection.hdel(key, hashes);
        } catch (JedisException e) {
            onError();
            throw e;
        }
    }

    @Override
    public Jedis getJedis() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.disconnect();
    }

    private void onError() {
        connection.disconnect();
    }

    private void checkIndex() {
        if(connection.getDB() != index) {
            LOGGER.warn("Invalid database detected(Got disconnected?..), correcting...");
            try {
                select(index);
            } catch (JedisException ignored){}
        }
    }

}

package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {

    T call(Jedis jedis);

}

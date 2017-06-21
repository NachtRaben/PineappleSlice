package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public interface RedisProvider {

        Redis getSession(int index) throws JedisException;

        void subscribe(String channel, RedisSubscriber subscriber) throws SubscriberException, JedisException;

        void psubscribe(String pattern, RedisSubscriber subscriber) throws SubscriberException, JedisException;

        void unSubscribe(RedisSubscriber subscriber);

        void publish(String channel, String data);

        void shutdown();

        JedisPool getPool();
}

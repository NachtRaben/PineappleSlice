package com.nachtraben.pineappleslice.redis;

import redis.clients.jedis.JedisPool;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public interface RedisProvider {

        Redis getSession(int index);

        void subscribe(String channel, RedisSubscriber subscriber) throws SubscriberException;

        void psubscribe(String pattern, RedisSubscriber subscriber) throws SubscriberException;

        void unSubscribe(RedisSubscriber subscriber);

        void publish(String channel, String data);

        void shutdown();

        JedisPool getPool();
}

package com.nachtraben.pineappleslice.redis.impl;

import com.nachtraben.pineappleslice.redis.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public class RedisProviderImpl implements RedisProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisProvider.class);

    private final Map<RedisSubscriber, JedisAdapter> subscribers;
    private final JedisPool pool;

    private final Thread thread;

    public RedisProviderImpl(RedisProperties properties) {
        thread = Thread.currentThread();
        subscribers = new HashMap<>();
        JedisPoolConfig config = new JedisPoolConfig();
        //config.setMaxWaitMillis(2000);
        //config.setBlockWhenExhausted(true);
        pool = new JedisPool(
                config,
                properties.getHost(),
                properties.getPort(),
                properties.getTimeout(),
                properties.getPassword());

    }

    @Override
    public Redis getSession(int index) {
        if (pool.isClosed())
            throw new IllegalStateException("The redis pool has already been shutdown!");

        Jedis connection = pool.getResource();
        return new RedisSessionImpl(connection, index);
    }

    @Override
    public void subscribe(String channel, RedisSubscriber subscriber) throws SubscriberException {
        checkStatus(subscriber);
        JedisAdapter adapter = new JedisAdapter(subscriber);
        subscribers.put(subscriber, adapter);
        doVoidCall(jedis -> jedis.subscribe(adapter, channel));
    }

    @Override
    public void psubscribe(String pattern, RedisSubscriber subscriber) throws SubscriberException {
        checkStatus(subscriber);
        JedisAdapter adapter = new JedisAdapter(subscriber);
        subscribers.put(subscriber, adapter);
        doVoidCall(jedis -> jedis.subscribe(adapter, pattern));
    }

    @Override
    public void unSubscribe(RedisSubscriber subscriber) {
        JedisAdapter adapter = subscribers.get(subscriber);
        if (adapter != null && adapter.isSubscribed()) {
            adapter.unsubscribe();
            adapter.punsubscribe();
        }
        subscribers.remove(subscriber);
    }

    @Override
    public void publish(String channel, String data) {
        doVoidCall(jedis -> jedis.publish(channel, data));
    }

    private void checkStatus(RedisSubscriber subscriber) throws SubscriberException {
        if (subscribers.get(subscriber) != null)
            throw new SubscriberException("Subscriber already subscribed");
    }

    @Override
    public void shutdown() {
        pool.destroy();
    }

    private void doVoidCall(Consumer<Jedis> call) {
        try (Jedis jedis = pool.getResource()) {
            call.accept(jedis);
        }
    }

    private <R> R doCall(Function<Jedis, R> call) {
        try (Jedis jedis = pool.getResource()) {
            return call.apply(jedis);
        }
    }

    @Override
    public JedisPool getPool() {
        return pool;
    }

}

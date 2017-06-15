package com.nachtraben.pineappleslice.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public class JedisAdapter extends JedisPubSub implements RedisSubscriber {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RedisSubscriber subscriber;

    public JedisAdapter(RedisSubscriber subscriber) {
        if(subscriber == null)
            throw new IllegalStateException("RedisSubscriber cannot be null.");
        this.subscriber = subscriber;
    }

    @Override
    public void onMessage(String channel, String message) {
        try {
            subscriber.onMessage(channel, message);
        } catch (Exception e) {
            logger.error("Failed to process message.", e);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        try {
            subscriber.onMessage(channel, message);
        } catch (Exception e) {
            logger.error("Failed to process message.", e);
        }
    }
}

package com.nachtraben.pineappleslice.redis;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public interface RedisSubscriber {

    void onMessage(String channel, String message);

}

package com.nachtraben.pineappleslice.redis;

import com.nachtraben.pineappleslice.redis.impl.RedisProviderImpl;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public class RedisModule {

    private RedisProperties properties;
    private RedisProvider provider;

    public RedisModule(RedisProperties properties) {
        this.properties = properties;
        provider = new RedisProviderImpl(properties);
    }

    public RedisProvider getProvider() {
        return provider;
    }
}

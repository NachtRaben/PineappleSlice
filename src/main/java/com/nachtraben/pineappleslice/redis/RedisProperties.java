package com.nachtraben.pineappleslice.redis;

/**
 * Created by NachtRaben on 4/20/2017.
 */
public class RedisProperties {

    private String host = "localhost";
    private int port = 6379;
    private String password = "";
    private int timeout = 10000;
    private int maxConnections = 8;

    public RedisProperties(String host, int port, String password, int timeout) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.timeout = timeout;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public int getTimeout() {
        return timeout;
    }
}

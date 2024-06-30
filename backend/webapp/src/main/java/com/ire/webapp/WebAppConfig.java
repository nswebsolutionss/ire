package com.ire.webapp;

public class WebAppConfig
{

    private final int port;
    private final String host;

    public WebAppConfig(final int port, final String host) {
        this.port = port;
        this.host = host;
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }
}

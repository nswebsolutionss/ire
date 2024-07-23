package com.ire.webapp;

public class WebAppConfig {

    private final int port;
    private final String host;

    private final String propelAuthBaseUri = "https://0939966.propelauthtest.com";
    private final String propelAuthAuthorizationEndpoint = propelAuthBaseUri + "/propelauth/oauth/authorize";
    private final String propelAuthTokenEndpoint = propelAuthBaseUri + "/propelauth/oauth/token";
    private final String propelAuthClientId = "531752b5a27b659e3592aeca6866d5a8";
    private final String propelAuthClientSecret = "531752b5a27b659e3592aeca6866d5a8095dd66b6f7fb132ea389b293c1600bbf386cea338c231ebb5afb9be052845f3";
    private final String propelAuthRedirectUrl = "http://localhost:8084/callback";
    private final String accountServiceUri = "http://localhost:8082";
    private final String publicKey;

    public static final String TEST_KEY = "IKOJEF723RIHJF8Jwefoijwef823ji4efhj823w43wder23f2ef";
    public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5LVqKm2A8ggAZM9ctrlp\n" +
            "lK+JhkwB+qQaon/VZDkRRzvLX8QckDWkWDgsqzn1FiEp/vpbSsfhoFP8/spNMn8m\n" +
            "diZ4GH6ExbnwkrUn0u5OLkJZncE3917R/t/e3sPfVZIXVEW1MRqD6vMrOyN9QRZK\n" +
            "gxaHnF8pvADRInI1bw0suQOCkMkqy9mk8WNZndWuVMUmHCsD/oS3gy1BFlVR39/m\n" +
            "rX7JOZTTloYBOZIDGrkPwn3VIYPbvMl3OcNrav3e4HC72nHFpeAGyEJe3RRamRVE\n" +
            "Co0KwkLZPxwMA74SRrt43kCY6PJZ4qI2ASimVQYsl+S0eVWfOe5UWhLXAt0mewPW\n" +
            "ZQIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    private final String privateKeyAlg;

    public WebAppConfig(final int port, final String host, final String publicKey, final String privateKeyAlg) {
        this.port = port;
        this.host = host;
        this.publicKey = publicKey;
        this.privateKeyAlg = privateKeyAlg;
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }

    public String accountServiceUri() {
        return accountServiceUri;
    }

    public String getPropelAuthAuthorizationEndpoint() {
        return propelAuthAuthorizationEndpoint;
    }

    public String getPropelAuthTokenEndpoint() {
        return propelAuthTokenEndpoint;
    }

    public String getPropelAuthClientId() {
        return propelAuthClientId;
    }

    public String getPropelAuthClientSecret() {
        return propelAuthClientSecret;
    }

    public String getPropelAuthRedirectUrl() {
        return propelAuthRedirectUrl;
    }

    public String privateKey() {
        return this.publicKey;
    }

    public String privateKeyAlg() {
        return this.privateKeyAlg;
    }
}

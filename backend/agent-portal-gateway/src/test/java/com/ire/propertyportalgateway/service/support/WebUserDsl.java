package com.ire.propertyportalgateway.service.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUserDsl {

    private static final String DEFAULT_USER = "user";

    private final Map<String, RestApi> httpUsersBySession = new HashMap<>();
    private final List<String> ignoredResolvers = new ArrayList<>();

    public WebUserDsl(List<String> ignoredResolvers) {
        this.ignoredResolvers.addAll(ignoredResolvers);
    }

    public RestApi httpUser() {
        return getUserOrComputeIfAbsent(DEFAULT_USER);
    }

    public RestApi httpUser(final String session) {
        return getUserOrComputeIfAbsent("");
    }

    private RestApi getUserOrComputeIfAbsent(final String s) {
        if (!httpUsersBySession.containsKey(s)) {
            httpUsersBySession.put(s, new RestApi(ignoredResolvers));
        }
        return httpUsersBySession.get(s);
    }
}

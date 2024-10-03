package com.ire.propertyportalgateway.service.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ire.propertyportalgateway.service.messages.MessageToReceive;
import com.ire.propertyportalgateway.service.messages.MessageToSend;
import com.ire.propertyportalgateway.service.messages.RequestMessage;
import com.ire.propertyportalgateway.service.messages.ResponseMessage;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.vertx.core.buffer.Buffer.buffer;

public class RestApi implements Handler<AsyncResult<HttpResponse<Buffer>>> {

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<RequestMessage> sentMessages = new ArrayList<>();
    private final List<CompletableFuture<HttpResponse<Buffer>>> pendingRequestList = new ArrayList<CompletableFuture<HttpResponse<Buffer>>>();
    private final List<HttpResponse<Buffer>> responses = new ArrayList<>();
    private final WebClient client;
    private CompletableFuture<HttpResponse<Buffer>> future = new CompletableFuture<>();
    private final List<String> ignoredResolvers = new ArrayList<>();
    public final static SupplemntaryData supplementaryData = new SupplemntaryData();

    public RestApi(List<String> ignoredResolvers) {
        Vertx vertx = Vertx.vertx();
        this.client = WebClient.create(vertx);
        this.ignoredResolvers.addAll(ignoredResolvers);
    }


    public void sends(final MessageToSend request) {
        RequestMessage messageToSend = request.toMessage();
        sentMessages.add(messageToSend);
        supplementaryData.addSentMessage(messageToSend);
        pendingRequestList.add(
                execAsync(messageToSend)
        );

    }

    private CompletableFuture<HttpResponse<Buffer>> execAsync(final RequestMessage request) {
        HttpRequest<Buffer> httpRequest = client.requestAbs(request.method(), "http://" + request.host() + ":" + request.port() + "/" + request.uri());
        try {
            if (!request.headers().isEmpty()) {
                if (request.headers().containsKey("access_token")) {
                    String accessToken = request.headers().remove("access_token");
                    httpRequest.putHeader("Cookie", "access_token=" + accessToken);
                }
                request.headers().forEach(httpRequest::putHeader);
            }
            if (!request.queryParams().isEmpty()) {
                request.queryParams().forEach(httpRequest::addQueryParam);
            }
            if (request.contentType() != null && request.payload() != null) {
                switch (request.contentType()) {
                    case JSON -> {
                        final String requestToJsonBody = Json.encode(request.payload());
                        httpRequest.sendJson(buffer(requestToJsonBody), this);
                    }
                    default -> throw new IllegalArgumentException("Unknown ContentType");
                }
            } else {
                httpRequest.followRedirects(false).send(this);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return handleFuture();
    }

    private CompletableFuture<HttpResponse<Buffer>> handleFuture() {
        this.future = new CompletableFuture<>();
        return future.handle((bufferHttpResponse, throwable) -> {
            if (throwable != null) {
                throw new RuntimeException(throwable);
            } else {
                return bufferHttpResponse;
            }

        });
    }

    public void receives(final MessageToReceive expected) {
        waitForResponseAndAssert(expected.toMessage());
    }

    private void waitForResponseAndAssert(final ResponseMessage expected) {

        Awaitility.await(Thread.currentThread().getName())
                .timeout(2, TimeUnit.SECONDS)
                .pollInterval(5, TimeUnit.MILLISECONDS)
                .untilAsserted(
                        () -> {

                            HttpResponse<Buffer> actual = getResponse();
                            Assertions.assertNotNull(actual);
                            ResponseMessage actualResponse = mapVertxResponseToResponse(actual, expected);
                            String encodedExpected = Json.encode(expected);
                            String encodedActual = Json.encode(actualResponse);

                            LOGGER.info("----------------------------------------------------------------------------");
                            LOGGER.info("");
                            LOGGER.info("EXPECTED: " + encodedExpected);
                            LOGGER.info("ACTUAL:   " + encodedActual);
                            LOGGER.info("----------------------------------------------------------------------------");
                            Assertions.assertEquals(expected.statusCode, actualResponse.statusCode);

                            if (actualResponse.contentBody != null || expected.contentBody != null) {
                                String actualEncoded = Json.encode(actualResponse.contentBody);
                                String expectedEncoded = Json.encode(expected.contentBody);

                                JSONObject expectedJson = new JSONObject(expectedEncoded);
                                JSONObject actualJson = new JSONObject(actualEncoded);

                                assertResponseBody(expectedJson, actualJson);
                            }


                            Assertions.assertEquals(expected.contentType, actualResponse.contentType);
                            expected.headers.forEach((k, v) -> {
                                if (ignoredResolvers.contains(k)) {
                                    Assertions.assertNotNull(actual.headers().get(k));
                                    supplementaryData.addReceivedHeader(k, actual.headers().get(k));
                                } else {
                                    Assertions.assertEquals(expected.headers.get(k), v);
                                }
                            });
                            responses.remove(0);
                            if (actualResponse.contentBody != null) {
                                supplementaryData.addReceivedMessage(new JSONObject(Json.encode(actualResponse.contentBody)));
                            }
                        }
                );
    }

    private void assertResponseBody(final JSONObject expected, final JSONObject actual) {
        expected.keys().forEachRemaining(key -> {
            if (ignoredResolvers.contains(key)) {
                Assertions.assertTrue(actual.has(key));
                return;
            }
            if (actual.get(key) instanceof JSONObject) {
                assertResponseBody((JSONObject) expected.get(key), (JSONObject) actual.get(key));
            } else if (actual.get(key) instanceof JSONArray) {
                Assertions.assertTrue(((JSONArray) actual.get(key)).isEmpty());
            } else {
                Assertions.assertEquals(expected.get(key), actual.get(key));
            }
        });


    }

    private ResponseMessage mapVertxResponseToResponse(final HttpResponse<Buffer> actual, final ResponseMessage expected) {
        Map<String, String> headers = new HashMap<>();
        String contentTypeRaw = "";

        expected.headers.forEach((k, v) -> {
            if (actual.headers().contains(k)) {
                headers.put(k, actual.headers().get((k)));
            }
        });

        for (Map.Entry<String, String> header : actual.headers()) {
            if (header.getKey().toLowerCase(Locale.ROOT).equals("content-type")) {
                contentTypeRaw = header.getValue();
            }
        }

        return new ResponseMessage(
                actual.statusCode(),
                mapContentType(contentTypeRaw),
                actual.bodyAsJson(Object.class),
                headers

        );
    }

    private static ContentType mapContentType(final String contentTypeRaw) {
        if (contentTypeRaw.toLowerCase(Locale.ROOT).equals("application/json")) {
            return ContentType.JSON;
        }
        return null;
    }

    private HttpResponse<Buffer> getResponse() {
        if (pendingRequestList.isEmpty() && responses.isEmpty()) {
        } else if (!responses.isEmpty()) {
            return responses.getLast();
        } else {
            while (!pendingRequestList.isEmpty()) {

                CompletableFuture<HttpResponse<Buffer>> pendingRequest = pendingRequestList.getLast();
                try {
                    HttpResponse<Buffer> bufferHttpResponse = pendingRequest.get(10, TimeUnit.SECONDS);
                    responses.add(bufferHttpResponse);
                    return bufferHttpResponse;
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    LOGGER.error("Failed to response pending http request in {} {}", 10, "s", e);
                }
            }

        }
        return null;
    }


    @Override
    public void handle(AsyncResult<HttpResponse<Buffer>> asyncResult) {
        try {
            if (asyncResult.succeeded()) {
                future.complete(asyncResult.result());
            } else {
                future.completeExceptionally(asyncResult.cause());
            }
        } catch (final Exception e) {
            future.completeExceptionally(e);
        }
    }

    public static class SupplemntaryData {
        private final List<RequestMessage> sentMessages = new ArrayList<>();
        private final List<JSONObject> receivedMessages = new ArrayList<>();
        private final Map<String, String> receivedHeaders = new HashMap<>();

        public void addSentMessage(final RequestMessage messageToSend) {
            sentMessages.add(messageToSend);
        }

        public void addReceivedMessage(final JSONObject received) {
            receivedMessages.add(received);
        }

        public void addReceivedHeader(final String key, final String value) {
            receivedHeaders.put(key, value);
        }

        public JSONObject getLastReceivedMessage() {
            return receivedMessages.getLast();
        }

        public Map<String, String> getLastReceivedHeader() {
            return receivedHeaders;
        }

    }
}

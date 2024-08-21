package com.ire.propertyportalgateway.service.support;

import com.ire.propertyportalgateway.service.HttpPublisher;
import com.ire.propertyportalgateway.service.HttpRequestMessage;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.impl.future.SucceededFuture;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.impl.HttpResponseImpl;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;


public class HttpPublisherStub implements HttpPublisher {

    List<HttpRequestMessage> requests = new ArrayList<>();

    public void receives(final HttpRequestMessage expectedRequest) {
        requests.forEach(request -> LogManager.getLogger().error(request));

    }


    @Override
    public void publish(HttpRequestMessage request, Handler<AsyncResult<HttpResponse<Buffer>>> requestHandler) {
        requests.add(request);

        String url = request.url();
        if (url.contains("/propelauth/oauth/token")) {
            requestHandler.handle(
                    new SucceededFuture<>(
                            new HttpResponseImpl<>(
                                    HttpVersion.HTTP_2,
                                    200,
                                    "",
                                    MultiMap.caseInsensitiveMultiMap(),
                                    MultiMap.caseInsensitiveMultiMap(),
                                    List.of(),
                                    new BufferImpl().appendString("{" +
                                            "\"access_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjYxODgzMzcxLWNiNTQtNDg2ZC1hZDdiLTM2MTAzYjYzM2ViNyJ9.eyJzdWIiOiIwZjBiNzJkMS1hMDAzLTRhYzUtYjRiZS04ZGM0ZWIyNTQyYTciLCJhdWQiOiI1MzE3NTJiNWEyN2I2NTllMzU5MmFlY2E2ODY2ZDVhOCIsImlhdCI6MTcyMTcyNjk2OSwiZXhwIjoxNzIxNzI4NzY5LCJ1c2VyX2lkIjoiMGYwYjcyZDEtYTAwMy00YWM1LWI0YmUtOGRjNGViMjU0MmE3IiwiaXNzIjoiaHR0cHM6Ly8wOTM5OTY2LnByb3BlbGF1dGh0ZXN0LmNvbSIsImVtYWlsIjoic3RldmllLWh1YmJsZUBob3RtYWlsLmNvLnVrIiwib3JnX2lkX3RvX29yZ19tZW1iZXJfaW5mbyI6eyJmOWUwOTNhZS0xMDU3LTQ0ZGMtOTQwYi1jNzZhOTU0OGE5NDMiOnsib3JnX2lkIjoiZjllMDkzYWUtMTA1Ny00NGRjLTk0MGItYzc2YTk1NDhhOTQzIiwib3JnX25hbWUiOiJFc3RhdGUgQWdlbnRzIFBybyIsInVybF9zYWZlX29yZ19uYW1lIjoiZXN0YXRlLWFnZW50cy1wcm8iLCJvcmdfbWV0YWRhdGEiOnt9LCJ1c2VyX3JvbGUiOiJPd25lciIsImluaGVyaXRlZF91c2VyX3JvbGVzX3BsdXNfY3VycmVudF9yb2xlIjpbIk93bmVyIiwiQWRtaW4iLCJNZW1iZXIiXSwib3JnX3JvbGVfc3RydWN0dXJlIjoic2luZ2xlX3JvbGVfaW5faGllcmFyY2h5IiwiYWRkaXRpb25hbF9yb2xlcyI6W10sInVzZXJfcGVybWlzc2lvbnMiOlsicHJvcGVsYXV0aDo6Y2FuX2ludml0ZSIsInByb3BlbGF1dGg6OmNhbl9jaGFuZ2Vfcm9sZXMiLCJwcm9wZWxhdXRoOjpjYW5fcmVtb3ZlX3VzZXJzIiwicHJvcGVsYXV0aDo6Y2FuX3NldHVwX3NhbWwiLCJwcm9wZWxhdXRoOjpjYW5fbWFuYWdlX2FwaV9rZXlzIiwicHJvcGVsYXV0aDo6Y2FuX2VkaXRfb3JnX2FjY2VzcyIsInByb3BlbGF1dGg6OmNhbl91cGRhdGVfb3JnX21ldGFkYXRhIiwicHJvcGVsYXV0aDo6Y2FuX3ZpZXdfb3RoZXJfbWVtYmVycyIsInByb3BlbGF1dGg6OmNhbl9kZWxldGVfb3JnIl19fX0.feoFU3TbjNdGbbdnrzRIgVpr4wNjpMVq1ySHdKC9J6kqNp6G8TtlBOU89Wsh5DcjQY1DCnE4EClUuwdkQzxNmnyeTSWSrzIuWkZtjoyBSZTTvZ-PFsoOpwPyqePOzAlozr39lhGIeSM1SpF2fs5rc5Eq-zoj8tsZZ6q7cbv3s_ECv49X_83pAEXwVZDkX2Yzrl9waeyciGyZr5m0Ge4g7FyO3tZGrt0XW_6qSyIVxt8uM3k-kJ72okVwgcgzIFzfK7pjERrDB14tP7EUaq3jhNsO-3fXDAWUyNFW2K61h8ZdKr-wWD9CXGDLsE2QKjr4TasVHh6PVPxNS1toxzXYCA\"," +
                                            "\"refresh_token\": \"oijwef23jf3f023f8fhf\"," +
                                            "\"id_token\": \"oijwefiojwefj032f329\"" +
                                            "}"
                                    ),
                                    List.of()
                            )
                    )
            );
        } else if (url.contains("http://localhost:8082")) {
            requestHandler.handle(
                    new SucceededFuture<>(
                            new HttpResponseImpl<>(
                                    HttpVersion.HTTP_2,
                                    200,
                                    "",
                                    MultiMap.caseInsensitiveMultiMap().add("Location", "http://localhost:5174/dashboard"),
                                    MultiMap.caseInsensitiveMultiMap(),
                                    List.of(),
                                    new BufferImpl(),
                                    List.of()
                            )
                    )
            );
        }

    }
}


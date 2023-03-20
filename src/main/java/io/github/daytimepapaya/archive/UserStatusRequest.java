package io.github.daytimepapaya.archive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daytimepapaya.util.Credential;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class UserStatusRequest {
    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private final Credential credential;

    public UserStatusRequest(Credential credential) {
        this.credential = credential;
    }

    public Optional<UserStatus> request() throws IOException {

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://web.archive.org/save/status/user")).newBuilder();
        urlBuilder.addQueryParameter("_t", String.valueOf(ThreadLocalRandom.current().nextInt(1, 100_000_000))); // To avoid getting a stale cache response

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .header("Accept", "application/json")
                .header("Authorization", "LOW " + credential.IA_S3_ACCESS_KEY() + ":" + credential.IA_S3_SECRET_KEY())
                .build();

        try (Response response = client.newCall(request).execute()) {

            ObjectMapper mapper = new ObjectMapper();
            UserStatus userStatus = mapper.readValue(Objects.requireNonNull(response.body()).string(), UserStatus.class);

            return Optional.of(userStatus);
        }
    }
}


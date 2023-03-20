package io.github.daytimepapaya.archive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daytimepapaya.util.Credential;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class StatusRequest {

    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private final Credential credential;

    public StatusRequest(Credential credential) {
        this.credential = credential;
    }

     public Optional<StatusResponse> request(String job_id) throws IOException {

        Request request = new Request.Builder()
                .url("https://web.archive.org/save/status/" + job_id)
                .header("Accept", "application/json")
                .header("Authorization", "LOW " + credential.IA_S3_ACCESS_KEY() + ":" + credential.IA_S3_SECRET_KEY())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ObjectMapper mapper = new ObjectMapper();
            StatusResponse statusResponse = mapper.readValue(Objects.requireNonNull(response.body()).string(), StatusResponse.class);

            return Optional.of(statusResponse);

        }
    }
}

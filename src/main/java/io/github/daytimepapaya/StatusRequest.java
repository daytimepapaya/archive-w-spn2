package io.github.daytimepapaya;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class StatusRequest {

    private final OkHttpClient client = new OkHttpClient();

    private final Credential credential;

    public StatusRequest(Credential credential) {
        this.credential = credential;
    }

    Optional<StatusResponse> request(String job_id) throws IOException {

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

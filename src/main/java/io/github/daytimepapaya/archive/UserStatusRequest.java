package io.github.daytimepapaya.archive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daytimepapaya.util.Credential;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class UserStatusRequest {
    private final OkHttpClient client = new OkHttpClient();

    private final Credential credential;

    public UserStatusRequest(Credential credential) {
        this.credential = credential;
    }

    public Optional<UserStatus> request() throws IOException {

        Request request = new Request.Builder()
                .url("http://web.archive.org/save/status/user")
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


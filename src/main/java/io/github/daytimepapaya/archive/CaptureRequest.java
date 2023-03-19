package io.github.daytimepapaya.archive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daytimepapaya.util.Credential;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CaptureRequest {

    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private final Credential credential;

    public CaptureRequest(Credential credential) {
        this.credential = credential;
    }

     public Optional<CaptureResponse> request(String url) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("url", url)
                .add("capture_all", "1")
                .add("capture_outlinks", "1")
                .add("capture_screenshot", "1")
                .add("delay_wb_availability", "1")
                .add("skip_first_archive", "1")
                .build();

        Request request = new Request.Builder()
                .url("https://web.archive.org/save")
                .header("Accept", "application/json")
                .header("Authorization", "LOW " + credential.IA_S3_ACCESS_KEY() + ":" + credential.IA_S3_SECRET_KEY())
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            CaptureResponse captureResponse = mapper.readValue(Objects.requireNonNull(response.body()).string(), CaptureResponse.class);

            return Optional.of(captureResponse);
        }
    }


}

package io.github.daytimepapaya;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class Archive {

    public final OkHttpClient client = new OkHttpClient();

    private Credential credential;

    public static void main(String[] args) throws IOException {
        if (args[0] == null)
            throw new RuntimeException("pass the url to archive");

        var archive = new Archive();
        archive.getCredential();
        archive.query(args[0]);
    }

    void query(String url2archive) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("url", url2archive)
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
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ObjectMapper mapper = new ObjectMapper();
            CaptureResponse captureResponse = mapper.readValue(Objects.requireNonNull(response.body()).string(), CaptureResponse.class);

            System.out.printf(String.valueOf(captureResponse));
        }
    }

    void getCredential() {
        Dotenv dotenv = Dotenv.load();
        credential = new Credential(dotenv.get("IA_S3_ACCESS_KEY"), dotenv.get("IA_S3_SECRET_KEY"));
    }

}

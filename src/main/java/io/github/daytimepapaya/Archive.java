package io.github.daytimepapaya;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class Archive {
    private final OkHttpClient client = new OkHttpClient();

    private String IA_S3_ACCESS_KEY;
    private String IA_S3_SECRET_KEY;

    public static void main(String[] args) throws IOException {
        if (args[0] == null)
            throw new RuntimeException("pass the url to archive");

        var archive = new Archive();
        archive.getPassAndKey();
        archive.query(args[0]);
    }

    void query(String url2archive) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("url", url2archive)
                .build();

        Request request = new Request.Builder()
                .url("https://web.archive.org/save")
                .header("Accept", "application/json")
                .header("Authorization", "LOW " + IA_S3_ACCESS_KEY + ":" + IA_S3_SECRET_KEY)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(Objects.requireNonNull(response.body()).string());
        }

    }

    void getPassAndKey() {
        Dotenv dotenv = Dotenv.load();
        IA_S3_ACCESS_KEY = dotenv.get("IA_S3_ACCESS_KEY");
        IA_S3_SECRET_KEY = dotenv.get("IA_S3_SECRET_KEY");

        if (IA_S3_ACCESS_KEY == null || IA_S3_SECRET_KEY == null)
            throw new RuntimeException("""
                    Create a .env file in the root of your project.
                    ex.)
                    # formatted as key=value
                    IA_S3_ACCESS_KEY=[my access key]
                    IA_S3_SECRET_KEY=[my secret key]
                    """);
    }
}

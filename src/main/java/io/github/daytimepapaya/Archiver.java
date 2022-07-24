package io.github.daytimepapaya;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.Optional;

public class Archiver {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args[0] == null)
            throw new RuntimeException("pass the url to archive");

        var archiver = new Archiver();
        var credential = archiver.getCredential();

        var captureRequest = new CaptureRequest(credential);
        Optional<CaptureResponse> captureResponse = captureRequest.request(args[0]);

        Thread.sleep(5000);

        if (captureResponse.isPresent() && captureResponse.get().job_id() != null) {
            var statusRequest = new StatusRequest(credential);
            statusRequest.request(captureResponse.get().job_id());
        }

    }

    Credential getCredential() {
        Dotenv dotenv = Dotenv.load();
        return new Credential(dotenv.get("IA_S3_ACCESS_KEY"), dotenv.get("IA_S3_SECRET_KEY"));
    }

}

package io.github.daytimepapaya.controller;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.daytimepapaya.archive.CaptureRequest;
import io.github.daytimepapaya.archive.CaptureResponse;
import io.github.daytimepapaya.archive.Credential;
import io.github.daytimepapaya.archive.StatusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class Archiver {

    private static final Logger logger = LoggerFactory.getLogger(Archiver.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args[0] == null)
            throw new RuntimeException("pass the url to archive");

        var archiver = new Archiver();
        var credential = archiver.getCredential();

        var captureRequest = new CaptureRequest(credential);
        Optional<CaptureResponse> captureResponse = captureRequest.request(args[0]);

        logger.info("captureResponse: {}", captureResponse);

        Thread.sleep(10000);

        if (captureResponse.isPresent() && captureResponse.get().job_id() != null) {
            var statusRequest = new StatusRequest(credential);
            var statusResponse = statusRequest.request(captureResponse.get().job_id());
            logger.info("statusResponse: {}", statusResponse);
        }

    }

    Credential getCredential() {
        Dotenv dotenv = Dotenv.load();
        return new Credential(dotenv.get("IA_S3_ACCESS_KEY"), dotenv.get("IA_S3_SECRET_KEY"));
    }

}

package io.github.daytimepapaya.tools;

import io.github.daytimepapaya.archive.CaptureRequest;
import io.github.daytimepapaya.archive.CaptureResponse;
import io.github.daytimepapaya.archive.StatusRequest;
import io.github.daytimepapaya.util.CredentialUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class SavePageNow2 {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveTools.class);

    public static void main(String[] args) {
        try {
            var credential = CredentialUtils.getCredential();

            var captureRequest = new CaptureRequest(credential);
            Optional<CaptureResponse> captureResponse = captureRequest.request(args[0]);

            logger.info("captureResponse: {}", captureResponse);

            Thread.sleep(10000);

            if (captureResponse.isPresent() && captureResponse.get().job_id() != null) {
                var statusRequest = new StatusRequest(credential);
                var statusResponse = statusRequest.request(captureResponse.get().job_id());
                logger.info("statusResponse: {}", statusResponse);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

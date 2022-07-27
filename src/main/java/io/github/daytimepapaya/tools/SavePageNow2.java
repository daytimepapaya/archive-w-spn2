package io.github.daytimepapaya.tools;

import io.github.daytimepapaya.archive.CaptureRequest;
import io.github.daytimepapaya.archive.CaptureResponse;
import io.github.daytimepapaya.archive.UserStatus;
import io.github.daytimepapaya.archive.UserStatusRequest;
import io.github.daytimepapaya.util.CredentialUtils;
import io.github.daytimepapaya.util.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class SavePageNow2 {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveTools.class);

    public static void main(String[] args) {

        var jdbi = DatabaseUtils.getJdbi();

        var urls = jdbi.withHandle(handle ->
                handle.select("select url from archive")
                        .mapTo(String.class)
                        .list()
        );

        var credential = CredentialUtils.getCredential();

        var captureRequest = new CaptureRequest(credential);
        var userStatusRequest = new UserStatusRequest(credential);
        urls.forEach(url -> {
            Optional<UserStatus> userStatus;
            Optional<CaptureResponse> captureResponse;
            try {
                userStatus = userStatusRequest.request();
                while (userStatus.isEmpty() || userStatus.get().available() == 0) {
                    Thread.sleep(10000);
                    userStatus = userStatusRequest.request();
                }
                captureResponse = captureRequest.request(url);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("captureResponse: {}", captureResponse);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

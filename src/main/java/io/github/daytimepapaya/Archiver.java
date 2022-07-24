package io.github.daytimepapaya;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

public class Archiver {
    public static void main(String[] args) throws IOException {
        if (args[0] == null)
            throw new RuntimeException("pass the url to archive");

        var archiver = new Archiver();
        var credential = archiver.getCredential();

//        var captureRequest = new CaptureRequest(credential);
//        captureRequest.request(args[0]);

        var statusRequest = new StatusRequest(credential);
        statusRequest.request("spn2-e55a3441640f48922512a2cced97be0849474245");
    }
    Credential getCredential() {
        Dotenv dotenv = Dotenv.load();
        return new Credential(dotenv.get("IA_S3_ACCESS_KEY"), dotenv.get("IA_S3_SECRET_KEY"));
    }

}

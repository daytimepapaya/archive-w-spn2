package io.github.daytimepapaya.util;

import io.github.cdimascio.dotenv.Dotenv;

public class CredentialUtils {
    public static Credential getCredential() {
        Dotenv dotenv = Dotenv.load();
        return new Credential(dotenv.get("IA_S3_ACCESS_KEY"), dotenv.get("IA_S3_SECRET_KEY"));
    }
}

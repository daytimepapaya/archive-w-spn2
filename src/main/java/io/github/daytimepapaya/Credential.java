package io.github.daytimepapaya;

import org.jetbrains.annotations.NotNull;

public record Credential(
        @NotNull(value = """
                Create a .env file in the root of your project.
                IA_S3_ACCESS_KEY="{my access key}"
                """)
        String IA_S3_ACCESS_KEY,
        @NotNull(value = """
                Create a .env file in the root of your project.
                IA_S3_SECRET_KEY="{my secret key}"
                """)
        String IA_S3_SECRET_KEY
) {
}

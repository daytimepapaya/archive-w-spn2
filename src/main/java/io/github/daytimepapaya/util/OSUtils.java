package io.github.daytimepapaya.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OSUtils {


    public static Path getDataDirectory() {
        if (isWindows()) {
            return Paths.get(System.getenv("LOCALAPPDATA"));
        } else {
            return Paths.get(System.getProperty("user.home"));
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}

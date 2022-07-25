package io.github.daytimepapaya.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OSUtils {

    public static Path getDataDirectory() {
        if (isWindows()) {
            return Paths.get(System.getenv("LOCALAPPDATA"), "spn2archive.sqlite3");
        } else {
            return Paths.get(System.getProperty("user.home"), "spn2archive.sqlite3");
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}

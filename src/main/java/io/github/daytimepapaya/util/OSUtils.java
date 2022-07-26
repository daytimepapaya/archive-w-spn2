package io.github.daytimepapaya.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OSUtils {

    private static final String SQLITE_DATASTORE = "spn2archive.sqlite3";

    public static Path getDataDirectory() {
        if (isWindows()) {
            return Paths.get(System.getenv("LOCALAPPDATA"), SQLITE_DATASTORE);
        } else {
            return Paths.get(System.getProperty("user.home"), SQLITE_DATASTORE);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}

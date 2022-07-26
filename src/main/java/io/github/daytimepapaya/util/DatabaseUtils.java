package io.github.daytimepapaya.util;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

import java.nio.file.Paths;

public class DatabaseUtils {

    private static final String SQLITE_DATASTORE = "spn2archive.sqlite3";

    public static Jdbi getJdbi() {
        return Jdbi.create("jdbc:sqlite:" + Paths.get(OSUtils.getDataDirectory().toString(), SQLITE_DATASTORE))
                .installPlugin(new SQLitePlugin());
    }
}

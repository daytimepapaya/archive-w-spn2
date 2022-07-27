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

    public static void createArchiveTable(Jdbi jdbi) {
        jdbi.useHandle(handle -> handle.execute("create table if not exists archive (id integer primary key autoincrement, url text unique)"));
    }
}

package io.github.daytimepapaya.tools;

import io.github.daytimepapaya.util.DatabaseUtils;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.Arrays;

public class RssAdder {
    public static void main(String[] args) {
        var jdbi = DatabaseUtils.getJdbi();
        jdbi.useHandle(handle -> {
            handle.execute("create table if not exists rss (id integer primary key autoincrement, url text unique, status text)");
            PreparedBatch batch = handle.prepareBatch("insert or ignore into rss(url) values(:url)");
            Arrays.stream(args).forEach(e -> batch.bind("url", e).add());
            batch.execute();
        });
    }
}

package io.github.daytimepapaya.tools;

import io.github.daytimepapaya.util.DatabaseUtils;

public class RssAdder {
    public static void main(String[] args) {
        var jdbi = DatabaseUtils.getJdbi();
        jdbi.useHandle(handle -> {
            handle.execute("create table if not exists rss (id integer primary key autoincrement, url text unique)");
            handle.execute("insert or ignore into rss(url) values(?)", args[0]);
        });
    }
}

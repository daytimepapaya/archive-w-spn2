package io.github.daytimepapaya.tools;

import io.github.daytimepapaya.util.DatabaseUtils;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.Arrays;

public class UrlAdder {
    public static void main(String[] args) {
        var jdbi = DatabaseUtils.getJdbi();
        
        DatabaseUtils.createArchiveTable(jdbi);
        
        jdbi.useHandle(handle -> {
            PreparedBatch batch = handle.prepareBatch("insert or ignore into archive(url) values(:url)");
            Arrays.stream(args).forEach(url -> batch.bind("url", url).add());
            batch.execute();
        });
    }
}

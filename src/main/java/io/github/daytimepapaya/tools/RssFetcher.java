package io.github.daytimepapaya.tools;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.github.daytimepapaya.util.DatabaseUtils;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public class RssFetcher {
    public static void main(String[] args) {
        var jdbi = DatabaseUtils.getJdbi();

        List<String> rssfeeds = jdbi.withHandle(handle ->
                handle.select("select url from rss")
                        .mapTo(String.class)
                        .list()
        );
        var urls = rssfeeds
                .stream()
                .map(rssfeed -> {
                    try {
                        URL feedUrl = new URL(rssfeed);
                        SyndFeedInput input = new SyndFeedInput();
                        SyndFeed feed = input.build(new XmlReader(feedUrl));
                        return feed
                                .getEntries()
                                .stream()
                                .map(SyndEntry::getUri)
                                .toList();
                    } catch (FeedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .toList();

        DatabaseUtils.createArchiveTable(jdbi);
        jdbi.useHandle(handle -> {
                    PreparedBatch batch = handle.prepareBatch("insert or ignore into archive(url) values(:url)");
                    urls.forEach(url -> batch.bind("url", url).add());
                    batch.execute();
                }
        );
    }
}

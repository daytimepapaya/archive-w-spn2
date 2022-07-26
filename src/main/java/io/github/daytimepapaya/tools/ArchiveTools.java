package io.github.daytimepapaya.tools;

import java.util.Arrays;

public class ArchiveTools {


    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
            return;
        }
        String[] rest = Arrays.copyOfRange(args, 1, args.length);
        switch (args[0]) {
            case "rss" -> RssFetcher.main(rest);
            case "rssadd" -> RssAdder.main(rest);
            case "spn" -> SavePageNow2.main(rest);
            case "help" -> usage();
            default -> {
                System.err.println("spn2: '" + args[0] + "' is not a spn2 command. See 'spn2 help'.");
                System.exit(1);
            }
        }
    }

    private static void usage() {
        System.out.println("""
                usage: spn2 <command> [args]...
                """);
    }

}

package io.github.daytimepapaya.archive;

public record UserStatus(
        int processing,
        int daily_captures_limit,
        int available,
        int daily_captures) {
}

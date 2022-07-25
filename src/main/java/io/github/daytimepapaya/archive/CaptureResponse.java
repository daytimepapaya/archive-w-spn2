package io.github.daytimepapaya.archive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CaptureResponse(
        String url,
        String job_id,
        String message) {
}
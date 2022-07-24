package io.github.daytimepapaya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
record CaptureResponse(
        String url,
        String job_id,
        String message) {
}
package io.github.daytimepapaya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusResponse(
        String status,
        String job_id,
        String original_url,
        String screenshot,
        String timestamp,
        float duration_sec,
        List<String> resources,
        boolean delay_wb_availability,
        int http_status,
        Map<String, Object> counters,
        Map<String, Object> outlinks,
        String exception,
        String status_ext,
        String message
) {
}

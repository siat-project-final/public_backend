package com.takoyakki.backend.domain.studyDiary.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SummaryAnthropicClient {
    @Value("${anthropic.api.key}")
    private String apiKey;

    private static final String ENDPOINT = "https://api.anthropic.com/v1/messages";

    public String createSummary(String text) {
        String response = "";
        try {
            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("x-api-key", apiKey);
            conn.setRequestProperty("anthropic-version", "2023-06-01");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = """
                {
                    "model": "claude-3-5-sonnet-20241022",
                    "max_tokens": 1024,
                    "messages": [
                        {"role": "user", "content": "전체 내용이 %s과 같을 때, 요약을 작성해줘."}
                    ]
                }
                """.formatted(text);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                response = br.lines().collect(Collectors.joining());
                return response.split("\"text\":\"")[1].split("\"")[0];
            }

        } catch (IOException e) {
            throw new RuntimeException("Anthropic API 호출 실패", e);
        }
    }

    public String extractContents(String text) {
        String extracted = text.split("정답\\s*:")[0].trim();

        if (extracted.isEmpty()) {
            throw new IllegalArgumentException("문제를 찾을 수 없습니다.");
        }

        return extracted;
    }

    public int extractAnswer(String text) {
        Pattern pattern = Pattern.compile("정답\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        throw new IllegalArgumentException("정답을 찾을 수 없습니다.");
    }
}

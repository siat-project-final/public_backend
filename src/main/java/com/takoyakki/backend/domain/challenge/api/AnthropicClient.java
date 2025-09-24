package com.takoyakki.backend.domain.challenge.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnthropicClient {
    @Value("${anthropic.api.key}")
    private String apiKey;

    private static final String ENDPOINT = "https://api.anthropic.com/v1/messages";

    public String createProblem(String subject, int difficulty) {
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
                        {"role": "user", "content": "난이도는 %d이고, 주제는 \\"%s\\"인 객관식 개념 문제를 하나 만들어줘. 문제와 선지 및 정답만 존재하고, 정답은 숫자 형식으로 주되 \\"정답 : (정답)\\"이라고 명시해줘. 선지는 1. AA 2.BB ... 형식으로 4개를 줘"}
                    ]
                }
                """.formatted(difficulty, subject);

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

    public String extractTitle(String text) {
        String extracted = text.replaceAll("\\\\n", "\n");

        // 문제와 선택지를 분리
        String[] lines = extracted.split("\n");
        StringBuilder question = new StringBuilder();
        List<String> choices = new ArrayList<>();

        Pattern choicePattern = Pattern.compile("^\\d+\\.\\s");

        for (String line : lines) {
            if (choicePattern.matcher(line).find()) {
                choices.add(line.trim());
            } else {
                question.append(line).append(" ");
            }
        }

        if (extracted.isEmpty()) {
            throw new IllegalArgumentException("문제를 찾을 수 없습니다.");
        }

        return question.toString().trim();
    }

    public List<String> extractChoice(String text) {
        String extracted = text.split("정답\\s*:")[0].trim();

        String title = text.replaceAll("\\\\n", "\n");

        // 문제와 선택지를 분리
        String[] lines = title.split("\n");
        StringBuilder question = new StringBuilder();
        List<String> choices = new ArrayList<>();

        Pattern choicePattern = Pattern.compile("^\\d+\\.\\s");

        for (String line : lines) {
            if (choicePattern.matcher(line).find()) {
                choices.add(line.trim());
            } else {
                question.append(line).append(" ");
            }
        }

        if (extracted.isEmpty()) {
            throw new IllegalArgumentException("문제를 찾을 수 없습니다.");
        }

        return choices;
    }

    public static void disableSslVerification() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // SSL context 설정
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // 호스트 이름 검증 무시
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}

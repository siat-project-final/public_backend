package com.takoyakki.backend;

import com.takoyakki.backend.common.auth.service.RedisService;
import com.takoyakki.backend.domain.studyDiary.api.SummaryAnthropicClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.takoyakki.backend.domain.challenge.repository.ProblemSolvingMapper;
import com.takoyakki.backend.domain.challenge.service.ChallengeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class test {
    private final ChallengeService challengeService;
    private final ProblemSolvingMapper problemSolvingMapper;
    private final RedisService redisService;
    private final SummaryAnthropicClient summaryAnthropicClient;


    @GetMapping("/test2")
    @ResponseBody
    public ResponseEntity<?> test2(@RequestParam int j) {
        int i = challengeService.insertChallengeProblem("Spring", j);

        return ResponseEntity.ok(i + "개의 문제를 추가했습니다. 현재 난이도는 " + j);

    }
    @GetMapping("/test3")
    @ResponseBody
    public ResponseEntity<?> test3() {
        String admin = redisService.getRefreshToken("admin");
        return ResponseEntity.ok(admin);

    }
    @GetMapping("/test4")
    @ResponseBody
    public ResponseEntity<?> test4() {
        String summary = summaryAnthropicClient.createSummary("JAVA는 JVM 기반 OOP언어입니다.");
        return ResponseEntity.ok(summary);

    }
}







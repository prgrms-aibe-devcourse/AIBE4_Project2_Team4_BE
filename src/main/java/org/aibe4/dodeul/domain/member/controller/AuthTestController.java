package org.aibe4.dodeul.domain.member.controller;

import java.util.Map;
import org.aibe4.dodeul.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class AuthTestController {

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal CustomUserDetails user) {
        // 이 엔드포인트는 authenticated()로 막혀있으니 user는 null이 아니어야 정상
        return Map.of(
                "memberId", user.getMemberId(),
                "email", user.getEmail(),
                "role", user.getRole().name());
    }
}

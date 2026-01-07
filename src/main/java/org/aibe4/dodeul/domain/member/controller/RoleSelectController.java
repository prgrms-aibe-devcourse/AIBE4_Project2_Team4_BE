package org.aibe4.dodeul.domain.member.controller;

import jakarta.servlet.http.HttpSession;
import org.aibe4.dodeul.domain.member.dto.AuthSessionKeys;
import org.aibe4.dodeul.domain.member.model.enums.Role;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/onboarding/role")
public class RoleSelectController {

    @GetMapping
    public String page(
            @AuthenticationPrincipal org.aibe4.dodeul.global.security.CustomUserDetails user) {
        // 로그인 상태면 (CustomUserDetails가 주입됨) 라우터로 보내기
        if (user != null) {
            return "redirect:/home"; // dashboard 말고 home 라우터 추천
        }
        return "auth/role-select";
    }

    @PostMapping
    public String select(@RequestParam Role role, HttpSession session) {
        session.setAttribute(AuthSessionKeys.SELECTED_ROLE, role);
        return "redirect:/auth";
    }
}

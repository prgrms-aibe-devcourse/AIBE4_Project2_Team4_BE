package org.aibe4.dodeul.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.aibe4.dodeul.domain.member.service.MemberService;
import org.aibe4.dodeul.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/onboarding/nickname")
public class NicknameOnboardingViewController {

    private final MemberService memberService;

    @GetMapping
    public String form(@AuthenticationPrincipal CustomUserDetails user) {
        // 로그인 되어 있어야만 접근 가능
        if (user == null) {
            return "redirect:/auth/login";
        }
        return "auth/nickname";
    }

    @PostMapping
    public String submit(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String nickname,
            RedirectAttributes ra) {
        if (user == null) {
            return "redirect:/auth/login";
        }

        // 최소 검증(세부 검증은 추후 강화 가능)
        if (nickname == null || nickname.isBlank()) {
            ra.addFlashAttribute("errorMessage", "닉네임을 입력해주세요.");
            return "redirect:/onboarding/nickname";
        }

        memberService.updateNickname(user.getMemberId(), nickname.trim());

        ra.addFlashAttribute("message", "닉네임 설정이 완료되었습니다.");
        return "redirect:/mypage/dashboard";
    }
}

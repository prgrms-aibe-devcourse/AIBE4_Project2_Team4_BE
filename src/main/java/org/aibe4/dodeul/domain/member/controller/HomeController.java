package org.aibe4.dodeul.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.aibe4.dodeul.domain.member.model.entity.Member;
import org.aibe4.dodeul.domain.member.model.repository.MemberRepository;
import org.aibe4.dodeul.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails user) {
        // 인증이 필요한 endpoint라 user는 보통 null이 아님
        Member member =
                memberRepository
                        .findById(user.getMemberId())
                        .orElseThrow(() -> new IllegalStateException("Logged-in member not found"));

        // TODO: nickname 컬럼을 nullable로 바꾼 뒤 아래 분기를 활성화하세요.
        // if (member.getNickname() == null || member.getNickname().isBlank()) {
        //     return "redirect:/onboarding/nickname";
        // }

        // 지금은 임시 닉네임(user_...)을 넣고 있으니 일단 마이페이지(또는 메인)로
        return "redirect:/mypage/dashboard";
    }
}

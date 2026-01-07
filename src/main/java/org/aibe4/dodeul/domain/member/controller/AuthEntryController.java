package org.aibe4.dodeul.domain.member.controller;

import jakarta.servlet.http.HttpSession;
import org.aibe4.dodeul.domain.member.dto.AuthSessionKeys;
import org.aibe4.dodeul.domain.member.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthEntryController {

    @GetMapping
    public String entry(HttpSession session, Model model) {
        Role role = (Role) session.getAttribute(AuthSessionKeys.SELECTED_ROLE);
        if (role == null) {
            return "redirect:/onboarding/role";
        }

        model.addAttribute("role", role);
        return "auth/auth-entry";
    }
}

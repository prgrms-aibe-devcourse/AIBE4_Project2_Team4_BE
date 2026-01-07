package org.aibe4.dodeul.global.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS
                .cors(Customizer.withDefaults())

                // CSRF: UI는 보호, API는 제외(개발 편의)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**"))

                // URL 권한
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(
                                                "/",
                                                "/error",
                                                "/css/**",
                                                "/js/**",
                                                "/images/**",
                                                "/favicon.ico",
                                                "/auth/**",
                                                "/api/auth/**",
                                                "/swagger-ui.html",
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/oauth2/**",
                                                "/login/oauth2/**",
                                                "/h2-console/**",

                                                // board 공개 조회 permitAll (dev 최신 반영)
                                                "/api/board/posts",
                                                "/api/board/posts/**",

                                                // 역할선택 및 진입 페이지
                                                "/onboarding/**")
                                        .permitAll()

                                        // 역할 기반
                                        .requestMatchers("/mypage/mentor/**")
                                        .hasRole("MENTOR")
                                        .requestMatchers("/mypage/mentee/**")
                                        .hasRole("MENTEE")

                                        // API 역할 분리 시
                                        .requestMatchers("/api/mentor/**")
                                        .hasRole("MENTOR")
                                        .requestMatchers("/api/mentee/**")
                                        .hasRole("MENTEE")
                                        .requestMatchers("/mypage/**", "/api/**")
                                        .authenticated()
                                        .anyRequest()
                                        .authenticated())

                // 세션 관리
                .sessionManagement(
                        session ->
                                session.sessionFixation(
                                                sessionFixation -> sessionFixation.migrateSession())
                                        .invalidSessionUrl("/auth/login?expired"))

                // 로그인/로그아웃
                .formLogin(
                        form ->
                                form.loginPage("/auth/login")
                                        .loginProcessingUrl("/login")
                                        .defaultSuccessUrl("/home", true)
                                        .permitAll())
                .logout(
                        logout ->
                                logout.logoutUrl("/logout")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                                        .logoutSuccessUrl("/auth/login"));

        // H2 console iframe 차단 방지
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    // 개발용 CORS (배포 전 도메인 제한)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*")); // TODO: 배포 시 도메인 제한
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

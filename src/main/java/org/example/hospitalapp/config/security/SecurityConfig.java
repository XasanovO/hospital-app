package org.example.hospitalapp.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        registry -> {
                            registry
                                    .requestMatchers("/", "/auth/**", "/js/**", "/css/**")
                                    .permitAll()
                                    .requestMatchers(
                                            "/patient/add"
                                    ).hasAnyRole("SUPPER_ADMIN","ADMIN")
                                    .requestMatchers(
                                            "/admission/info"
                                    ).hasAnyRole("ADMIN","PATIENT")
                                    .requestMatchers(
                                            "/supperAdmin/**",
                                            "/patient/delete",
                                            "/patient/edit",
                                            "/doctor/add",
                                            "/doctor/edit",
                                            "/doctor/delete",
                                            "/speciality/**"
                                    ).hasRole("SUPPER_ADMIN")
                                    .requestMatchers(
                                            "/adminstrator",
                                            "/admission/subscribe",
                                            "/admission/enter"
                                    ).hasRole("ADMIN")
                                    .requestMatchers(
                                            "/patient"
                                    ).hasRole("PATIENT")
                                    .requestMatchers(
                                            "/doctor",
                                            "procedure/add",
                                            "admission/complete"
                                    )
                                    .hasRole("DOCTOR")
                                    .anyRequest()
                                    .authenticated();
                        }
                ).formLogin(formLogin ->
                        formLogin
                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/", true) // Muvaffaqiyatli login uchun yo'nalish
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}

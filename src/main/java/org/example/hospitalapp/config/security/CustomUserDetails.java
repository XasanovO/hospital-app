package org.example.hospitalapp.config.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.hospitalapp.repo.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhone(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

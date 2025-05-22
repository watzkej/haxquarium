package com.haxquarium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration for the application.
 * Note: This configuration is intentionally insecure for training purposes.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure security filter chain.
     * 
     * @param http The HttpSecurity to configure
     * @return The configured SecurityFilterChain
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Intentionally insecure configuration for training purposes
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/home", "/register", "/login", "/about", "/css/**", "/js/**", "/images/**", "/webjars/**", "/h2-console/**").permitAll()
                .requestMatchers("/products/**", "/categories/**").permitAll()
                .requestMatchers("/cart/**", "/checkout/**", "/profile/**").authenticated()
                // Intentionally insecure: Admin endpoints should be properly secured
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // Intentionally disabled for H2 console access
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                // Intentionally disabled for training purposes
                .disable()
            )
            .headers(headers -> headers
                // Intentionally disabled for H2 console access
                .frameOptions(frameOptions -> frameOptions.disable())
            );
        
        return http.build();
    }
    
    /**
     * Password encoder bean.
     * 
     * @return The password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

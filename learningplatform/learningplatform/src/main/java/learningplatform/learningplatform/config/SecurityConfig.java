package learningplatform.learningplatform.config;

import learningplatform.learningplatform.UserService.UserService;
import learningplatform.learningplatform.loginHistory.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public SecurityConfig(UserService userService, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.userService = userService;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // always keep session for web logins
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/api/courses/all",
                                "/api/courses/add/**",
                                "api/admin/courses/**",
                                "api/admin/delete/**",
                                "/api/admin/logins/**",
                                "/api/courses/buy/**",
                                "/api/courses/all/java",
                                "api/routes/**",
                                "api/routes/all",
                                "/api/routes/paths",
                                "/api/admin/block/**",
                                "/metrics/api",
                                "/rate-limit/metrics",
                                "/rate-limit/admin"

                        ).permitAll()
                        .requestMatchers("/api/admin/dashboard").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}) // optional: allows basic auth
                .formLogin(form -> form.permitAll()) // optional: enable form-based login
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)

                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

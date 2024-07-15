package com.timetracker.tracker.conf;

import com.timetracker.tracker.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.timetracker.tracker.utils.Constants.IGNORE_URLS;

/**
 * Configuration class for web security.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    /**
     * Authorization filter bean.
     *
     * @see com.timetracker.tracker.filters.JwtAuthFilter
     */
    private final JwtAuthFilter authFilter;
    /**
     * User loader bean.
     *
     * @see com.timetracker.tracker.services.impl.UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;

    /**
     * Configures the security filter chain for HttpSecurity.
     *
     * @param httpSecurity The HttpSecurity object.
     * @return SecurityFilterChain object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(List.of("*"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(IGNORE_URLS).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN_ROLE")
                        .requestMatchers("/api/tracker/project/**", "/api/tracker/record/**", "/api/tracker/user/**").authenticated()
                        .anyRequest().permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(entryPoint()));
        return httpSecurity.build();
    }

    /**
     * Creates and configures the AuthenticationProvider.
     *
     * @return AuthenticationProvider object.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates and configures the AuthenticationManager.
     *
     * @param config the AuthenticationConfiguration object.
     * @return AuthenticationManager object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates and configures the PasswordEncoder.
     *
     * @return PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a TrackerAccessDeniedHandler bean.
     *
     * @return TrackerAccessDeniedHandler object.
     */
    @Bean
    public TrackerAccessDeniedHandler accessDeniedHandler() {
        return new TrackerAccessDeniedHandler();
    }

    /**
     * Creates a ForbiddenEntryPoint bean.
     *
     * @return ForbiddenEntryPoint object.
     */
    @Bean
    public ForbiddenEntryPoint entryPoint() {
        return new ForbiddenEntryPoint();
    }

}

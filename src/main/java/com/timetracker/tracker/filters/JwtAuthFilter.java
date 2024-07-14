package com.timetracker.tracker.filters;

import com.timetracker.tracker.conf.JwtProvider;
import com.timetracker.tracker.exceptions.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.timetracker.tracker.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            String header = request.getHeader(TOKEN_HEADER);
            if (StringUtils.isBlank(header) || !StringUtils.startsWith(header, TOKEN_TYPE)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = header.substring(TOKEN_TYPE.length());
            if (!jwtProvider.validateAccessToken(jwt)) {
                throw new UnauthorizedException();
            }

            String email = jwtProvider.getAccessClaims(jwt).getSubject();
            if (StringUtils.isNotBlank(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (!auth.getPrincipal().equals(email)) {
                    throw new JwtException(TOKEN_WAS_STOLEN_MESSAGE);
                }
            }
        } catch (UnauthorizedException | JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("/api/tracker/auth");
    }
}

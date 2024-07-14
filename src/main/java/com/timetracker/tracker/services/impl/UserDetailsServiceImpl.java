package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(StringUtils.EMPTY);
        return user;
    }
}

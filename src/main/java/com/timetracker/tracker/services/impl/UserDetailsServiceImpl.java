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

/**
 * This class implements the UserDetailsService interface and is used to load user details by username.
 * It uses UserRepository for retrieving user details from the database.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * UserRepository bean.
     *
     * @see com.timetracker.tracker.repositories.UserRepository
     */
    private final UserRepository userRepository;

    /**
     * This method loads user details by the given email.
     *
     * @param email The email of the user to load.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException If user with the given email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(StringUtils.EMPTY);
        return user;
    }
}

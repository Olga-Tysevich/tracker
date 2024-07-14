package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.User;

public interface JwtService {

    LoggedUserDTO generatePairOfTokens(User user);

    LoggedUserDTO regeneratePairOfTokens(String refreshToken);
}

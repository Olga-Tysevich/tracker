package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;

public interface AuthService {

    LoggedUserDTO loginUser(UserLoginDTO req);

    LoggedUserDTO reLoginUser(String refreshToken);
}

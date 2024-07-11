package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(CreateUserDTO req);

    void deleteUser(Long id);

    void updateUser(UpdateUserDTO req);

    UserDTO getUserById(Long id);

    UsersForPageDTO getUsersForPage(GetUsersForPageDTO req);

}

package com.soulsoftware.blog.photoapp.api.users.service;

import com.soulsoftware.blog.photoapp.api.users.shared.UserDto;

public interface UsersService {
    UserDto createUser(UserDto userDetails);
}

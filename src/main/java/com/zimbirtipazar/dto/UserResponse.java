package com.zimbirtipazar.dto;

import com.zimbirtipazar.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String username;

    private Role role;

    private boolean approved;
}

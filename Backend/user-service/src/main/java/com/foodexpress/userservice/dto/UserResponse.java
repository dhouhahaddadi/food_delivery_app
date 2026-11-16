package com.foodexpress.userservice.dto;

import com.foodexpress.userservice.entity.AccountStatus;
import com.foodexpress.userservice.entity.Role;
import com.foodexpress.userservice.entity.UserProfile;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        Long id, UUID accountId, String firstName, String lastName, String email,
        String phone, Role role, AccountStatus status, LocalDateTime createdAt) {

    public static UserResponse from(UserProfile u) {
        return new UserResponse(u.getId(), u.getAccountId(), u.getFirstName(), u.getLastName(),
                u.getEmail(), u.getPhone(), u.getRole(), u.getStatus(), u.getCreatedAt());
    }
}

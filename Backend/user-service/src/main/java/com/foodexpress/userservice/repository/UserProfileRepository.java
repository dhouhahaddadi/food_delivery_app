package com.foodexpress.userservice.repository;

import com.foodexpress.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByEmail(String email);
    boolean existsByAccountId(java.util.UUID accountId);
}

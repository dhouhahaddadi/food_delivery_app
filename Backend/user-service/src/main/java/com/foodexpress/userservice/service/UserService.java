package com.foodexpress.userservice.service;

import com.foodexpress.userservice.dto.UserRequest;
import com.foodexpress.userservice.dto.UserResponse;
import com.foodexpress.userservice.entity.UserProfile;
import com.foodexpress.userservice.exception.ConflictException;
import com.foodexpress.userservice.exception.NotFoundException;
import com.foodexpress.userservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserProfileRepository users;

    public UserService(UserProfileRepository users) {
        this.users = users;
    }

    public UserResponse create(UserRequest r) {
        if (users.existsByEmail(r.email())) {
            throw new ConflictException("Email déjà utilisé : " + r.email());
        }
        if (users.existsByAccountId(r.accountId())) {
            throw new ConflictException("Un profil existe déjà pour ce compte");
        }
        UserProfile u = new UserProfile();
        u.setAccountId(r.accountId());
        u.setFirstName(r.firstName());
        u.setLastName(r.lastName());
        u.setEmail(r.email());
        u.setPhone(r.phone());
        u.setRole(r.role());
        return UserResponse.from(users.save(u));
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        return UserResponse.from(find(id));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> list() {
        return users.findAll().stream().map(UserResponse::from).toList();
    }

    /** Un compte n'est jamais supprimé : il passe à DISABLED. */
    public void disable(Long id) {
        find(id).disable();
    }

    private UserProfile find(Long id) {
        return users.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur introuvable : " + id));
    }
}

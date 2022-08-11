package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    boolean existsByPhoneNumber(String phoneNumber);

    User findUserById(Long id);

    User.UserPassword findPasswordByUserId(String userId);

    User.UserId findIdByUserId(String userId);

    boolean existsByIdAndPhoneNumber(Long userId, String password);

    User findAllById(Long userId);
}
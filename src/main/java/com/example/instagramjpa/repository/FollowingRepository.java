package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowingRepository extends JpaRepository<Following, Long> {
    Long countByUserId(Long userId);

    Long countByFollowUserId(Long userId);
}
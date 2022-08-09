package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByUserIdAndBoardId(Long userId,Long BoardId);
}
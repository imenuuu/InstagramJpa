package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {
    String findImgUrlByBoardId(Long boardId);
}
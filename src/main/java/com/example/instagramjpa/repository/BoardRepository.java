package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Long countByUserId(Long userId);

    @Query(value="select distinct b from Board b " +
            "join fetch User U" +
            "left join fetch BoardImg BI where b.user.userId=:userId")
    List<Board> findAllByUserId(Long userId);

    List<Board> findIdByUserId(Long userId);
}
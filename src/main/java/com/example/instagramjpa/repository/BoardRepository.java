package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.Board;
import org.springframework.data.domain.Pageable;
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


    @Query(value="select b from Board b " +
            "join fetch User U on U.id=b.user.id " +
            "where b.user.id in (select F.followUser.id from Following F where F.user.id=:id ) " +
            "and b.status=:status and b.suspensionStatus=:suspension order by b.createdDate desc")
    List<Board> findAllByUserIdAndStatusAndSuspensionStatusOrderByCreatedDateDescWithPagination(Long id, String status, String suspension, Pageable pageable);


    Board findAllById(Long boardId);
}
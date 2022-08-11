package com.example.instagramjpa.repository;

import com.example.instagramjpa.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value="select * from Comment C join User U on U.id=C.userId " +
            "where C.boardId=:boardId and C.status=:status and C.suspensionStatus=:suspension " +
            "order by C.createdDate desc",nativeQuery = true)
    List<Comment> findAllByBoardIdAndStatusAndSuspensionStatusOrderByCreatedDateDescWithPagination(Long boardId,String status,String suspension,Pageable pageable);
}
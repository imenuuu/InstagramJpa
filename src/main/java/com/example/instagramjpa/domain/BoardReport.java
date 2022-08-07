package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BoardReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reportId", nullable = false)
    private ReportList report;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Column(name = "status", length = 45)
    private String status;


}
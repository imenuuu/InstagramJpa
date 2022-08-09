package com.example.instagramjpa.service;

import com.example.instagramjpa.domain.Board;
import com.example.instagramjpa.domain.BoardImg;
import com.example.instagramjpa.domain.BoardLike;
import com.example.instagramjpa.dto.GetBoardRes;
import com.example.instagramjpa.repository.BoardLikeRepository;
import com.example.instagramjpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public List<GetBoardRes> getMainBoard(Long userId) {
        List<Board> followUserId=boardRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        List<GetBoardRes> getBoardResList=new ArrayList<>();

        for (Board B : followUserId){
            if(boardLikeRepository.existsByUserIdAndBoardId(userId,B.getId())){
                getBoardResList.add(GetBoardRes.from(B,true));
            }
            else{
                getBoardResList.add(GetBoardRes.from(B,false));
            }
        }

        return getBoardResList;
    }
}

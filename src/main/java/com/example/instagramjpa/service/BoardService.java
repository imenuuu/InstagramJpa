package com.example.instagramjpa.service;

import com.example.instagramjpa.domain.Board;
import com.example.instagramjpa.domain.BoardImg;
import com.example.instagramjpa.domain.BoardLike;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.dto.GetBoardRes;
import com.example.instagramjpa.dto.PostBoardReq;
import com.example.instagramjpa.repository.BoardImgRepository;
import com.example.instagramjpa.repository.BoardLikeRepository;
import com.example.instagramjpa.repository.BoardRepository;
import com.example.instagramjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardImgRepository boardImgRepository;
    private final UserRepository userRepository;

    public List<GetBoardRes> getMainBoard(Long userId, Pageable pageable) {

        List<Board> followUserId=boardRepository.findAllByUserIdAndStatusAndSuspensionStatusOrderByCreatedDateDescWithPagination(userId,"TRUE","FALSE",pageable);
        List<GetBoardRes> getBoardResList=new ArrayList<>();

        for (Board B : followUserId){
            if(boardLikeRepository.existsByUserIdAndBoardId(userId,B.getId())){
                getBoardResList.add(GetBoardRes.toArray(B,true));
            }
            else{
                getBoardResList.add(GetBoardRes.toArray(B,false));
            }
        }

        return getBoardResList;
    }

    @Transactional(rollbackFor= {SQLException.class, Exception.class})
    public Long createBoard(PostBoardReq postBoardReq) {
        User user =userRepository.getOne(postBoardReq.getUserId());
        Board board=Board.builder()
                .user(user)
                .description(postBoardReq.getDescription())
                .build();
        return boardRepository.save(board).getId();
        }

    @Transactional(rollbackFor= {SQLException.class, Exception.class})
    public void createBoardImg(Long userId, Long lastInsertId, String boardImgUrl) {
        User user =userRepository.getOne(userId);
        Board board=boardRepository.getOne(lastInsertId);

        BoardImg boardImg=BoardImg.builder()
                .user(user)
                .boardImgurl(boardImgUrl)
                .board(board)
                .build();
        boardImgRepository.save(boardImg);
    }
}

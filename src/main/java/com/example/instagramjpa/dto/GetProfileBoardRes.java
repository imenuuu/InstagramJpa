package com.example.instagramjpa.dto;

import lombok.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetProfileBoardRes  {
    private Long boardId;
    private List<String> imgUrl;

}

package com.jghan.instaclone.web.dto.comment;
//댓글 입력을 받기위한 dto

import lombok.Data;

@Data
public class CommentDto {
    private String content;
    private int imageId;

}

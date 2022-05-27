package com.jghan.instaclone.web.dto.comment;
//댓글 입력을 받기위한 dto

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    //NotNull: NULL값 체크
    //NotEmpty: 빈값이거나 null체크
    //NotBlank: 빈값이거나 null, 그리고 빈공백(스페이스) 체크

    @NotBlank
    private String content;
    @NotNull
    private Integer imageId;

}

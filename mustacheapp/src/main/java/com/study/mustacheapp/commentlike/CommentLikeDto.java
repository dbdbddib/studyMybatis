package com.study.mustacheapp.commentlike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDto implements ICommentLike {
    private Long id;
    private String commentTbl;
    private String nickname;
    private Long commentId;
}

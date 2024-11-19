package com.softagape.mustacheajax.commentlike;

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
    private Long createId;
    private Long commentId;
}

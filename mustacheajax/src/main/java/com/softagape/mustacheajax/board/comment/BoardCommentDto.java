package com.softagape.mustacheajax.board.comment;

import com.softagape.mustacheajax.commons.dto.BaseDto;
import jakarta.validation.constraints.Size;
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
public class BoardCommentDto extends BaseDto implements IBoardComment {
    private Long id;
    @Size(min = 4, max = 1000, message = "댓글은 10~1000자 입니다.")
    private String comment;
    private Integer likeQty;
    private Long boardId;
    private Long commentId;
    public String getTbl() {
        return "boardcomment";
    }
}

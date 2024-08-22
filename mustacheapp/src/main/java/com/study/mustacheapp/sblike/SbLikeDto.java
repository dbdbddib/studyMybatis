package com.study.mustacheapp.sblike;

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
public class SbLikeDto implements ISbLike {
    private Long id;
    private String tbl;
    private String nickname;
    private Long boardId;
}

package com.study.mustacheapp.sblike;

import com.study.mustacheapp.commons.inif.IServiceCRUD;

public interface ISbLikeService extends IServiceCRUD<ISbLike> {
    Boolean deleteByTableUserBoard(SbLikeDto dto);
    Integer countByTableUserBoard(ISbLike searchDto);
}

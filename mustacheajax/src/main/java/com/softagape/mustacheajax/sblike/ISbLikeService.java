package com.softagape.mustacheajax.sblike;

import com.softagape.mustacheajax.commons.inif.IServiceCRUD;

public interface ISbLikeService extends IServiceCRUD<ISbLike> {
    Boolean deleteByTableUserBoard(SbLikeDto dto);
    Integer countByTableUserBoard(ISbLike searchDto);
}

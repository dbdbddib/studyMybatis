package com.study.mustacheapp.commons.inif;

import com.study.mustacheapp.commons.dto.CUDInfoDto;

public interface IServiceCRUD<T> {
    T insert(CUDInfoDto info, T dto);
    T update(CUDInfoDto info, T dto);
    Boolean updateDeleteFlag(CUDInfoDto info, T dto);
    Boolean deleteById(Long id);
    T findById(Long id);
}

package com.softagape.mustacheajax.commons.inif;

import com.softagape.mustacheajax.commons.dto.ResponseCode;
import com.softagape.mustacheajax.commons.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICommonRestController<Req> extends IResponseController {
    ResponseEntity<ResponseDto> insert(Model model, @RequestBody Req dto);
    ResponseEntity<ResponseDto> update(Model model, @PathVariable Long id, @RequestBody Req dto);
    ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @PathVariable Long id, @RequestBody Req dto);
    ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id);
    ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id);
}

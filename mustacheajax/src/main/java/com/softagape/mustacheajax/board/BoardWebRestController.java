package com.softagape.mustacheajax.board;

import com.softagape.mustacheajax.commons.exeption.IdNotFoundException;
import com.softagape.mustacheajax.commons.exeption.LoginAccessException;
import com.softagape.mustacheajax.sbfile.SbFileDto;
import com.softagape.mustacheajax.sblike.SbLikeDto;
import com.softagape.mustacheajax.sblike.ISbLikeService;
import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.dto.ResponseCode;
import com.softagape.mustacheajax.commons.dto.ResponseDto;
import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import com.softagape.mustacheajax.commons.inif.ICommonRestController;
import com.softagape.mustacheajax.member.IMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardWebRestController implements ICommonRestController<BoardDto> {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private ISbLikeService sbLikeService;

    @Override
    public ResponseEntity<ResponseDto> insert(Model model, @RequestPart(value = "boardDto") BoardDto dto) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> insert(Model model
            , @Validated @RequestPart(value="boardDto") BoardDto dto
            , @RequestPart(value="files", required = false) List<MultipartFile> files
    ) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IBoard result = this.boardService.insert(cudInfoDto, dto, files);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, BoardDto dto) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @Validated @PathVariable Long id
            , @Validated @RequestPart(value="boardDto") BoardDto dto
            , @RequestPart(value="sbfiles", required = false) List<SbFileDto> sbFileDtoList
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            if (id == null || dto == null || dto.getId() == null || dto.getId() <= 0 || !id.equals(dto.getId())) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckSelfOrAdmin(model, find);
            IBoard result = this.boardService.update(cudInfoDto, dto, sbFileDtoList, files);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/deleteFlag/{id}")
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @Validated @PathVariable Long id, @Validated @RequestBody BoardDto dto) {
        try {
            if (id == null || dto == null || dto.getId() == null || dto.getId() <= 0 || !id.equals(dto.getId()) || dto.getDeleteFlag() == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckSelfOrAdmin(model, find);
            Boolean result = this.boardService.updateDeleteFlag(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            makeResponseCheckSelfOrAdmin(model, find);
            Boolean result = this.boardService.deleteById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.addViewQty(id);
            IBoard result = this.getBoardAndLike(id, cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @Validated @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if (searchAjaxDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            makeResponseCheckLogin(model);
            Integer result = this.boardService.countAllByNameContains(searchAjaxDto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @Validated @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if (searchAjaxDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            makeResponseCheckLogin(model);
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", searchAjaxDto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            this.boardService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.addLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            this.boardService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.subLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    private IBoard getBoardAndLike(Long id, IMember loginUser) {
        IBoard result = this.boardService.findById(id);
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(loginUser.getId())
                .boardId(id)
                .build();
        Integer likeCount = this.sbLikeService.countByTableUserBoard(boardLikeDto);
        result.setDeleteDt(likeCount.toString());
        return result;
    }
}

package com.study.mustacheapp.board;

import com.study.mustacheapp.sbfile.SbFileDto;
import com.study.mustacheapp.sblike.SbLikeDto;
import com.study.mustacheapp.sblike.ISbLikeService;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.ResponseCode;
import com.study.mustacheapp.commons.dto.ResponseDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.ICommonRestController;
import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.MemberRole;
import com.study.mustacheapp.sbfile.ISbFileService;
import com.study.mustacheapp.security.config.SecurityConfig;
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

    @Autowired
    private ISbFileService sbFileService;

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
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.insert(cudInfoDto, dto);
            if (result == null) {
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R000011, "서버 입력 에러", null);
            }
            this.sbFileService.insertFiles(result, files);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, BoardDto dto) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @PathVariable Long id
            , @Validated @RequestPart(value="boardDto") BoardDto dto
            , @RequestPart(value="sbfiles", required = false) List<SbFileDto> sbFileDtoList
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            if (id == null || dto == null || dto.getId() == null || dto.getId() <= 0 || !id.equals(dto.getId())) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getId().equals(find.getCreateId())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자와 본인만 수정 가능", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.update(cudInfoDto, dto);
            if (result == null) {
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R000021, "서버 수정 에러", null);
            }
            this.sbFileService.updateFiles(sbFileDtoList);
            this.sbFileService.insertFiles(result, files);
            ResponseDto res = ResponseDto.builder().message("ok").responseData(result).build();
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/deleteFlag/{id}")
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @PathVariable Long id, @RequestBody BoardDto dto) {
        try {
            if (id == null || dto == null || dto.getId() == null || dto.getId() <= 0 || !id.equals(dto.getId()) || dto.getDeleteFlag() == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getId().equals(find.getCreateId())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자와 본인만 삭제 가능", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.boardService.updateDeleteFlag(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자 권한 필요", null);
            }
            Boolean result = this.boardService.deleteById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            this.boardService.addViewQty(id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if (result == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }

            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if (searchAjaxDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            Integer result = this.boardService.countAllByNameContains(searchAjaxDto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    // 리스트 출력 부분 ( 이 메소드 먼저 실행 후 boardService.findAllByNameContains(searchAjaxDto) 이 문장에서 service 실행  )
    @PostMapping("/searchName")
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if (searchAjaxDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }

            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);

            if (list == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "서버 검색 에러", null);
            }

            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);

            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", searchAjaxDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.addLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if (result == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.subLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if (result == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    private IBoard getBoardAndLike(Long id, IMember loginUser) {
        IBoard result = this.boardService.findById(id);
        if (result == null) {
            return null;
        }
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

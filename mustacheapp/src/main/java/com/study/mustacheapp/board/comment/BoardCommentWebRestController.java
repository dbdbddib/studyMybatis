package com.study.mustacheapp.board.comment;

import com.study.mustacheapp.commentlike.CommentLikeDto;
import com.study.mustacheapp.commentlike.ICommentLikeService;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.ResponseCode;
import com.study.mustacheapp.commons.dto.ResponseDto;
import com.study.mustacheapp.commons.inif.ICommonRestController;
import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.MemberRole;
import com.study.mustacheapp.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/boardcomment")
public class BoardCommentWebRestController implements ICommonRestController<BoardCommentDto> {
    @Autowired
    private IBoardCommentService boardCommentService;

    @Autowired
    private ICommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestBody BoardCommentDto dto) {
        try {
            if ( dto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoardComment result = this.boardCommentService.insert(cudInfoDto, dto);
            if ( result == null ) {
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R000011, "서버 입력 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @PathVariable Long id, @Validated @RequestBody BoardCommentDto dto) {
        try {
            if ( id == null || dto == null || !id.equals(dto.getId()) ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getNickname().equals(find.getCreateId()) ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자와 본인만 수정 가능", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoardComment result = this.boardCommentService.update(cudInfoDto, dto);
            if ( result == null ) {
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R000021, "서버 수정 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/deleteFlag/{id}")
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @PathVariable Long id, @RequestBody BoardCommentDto dto) {
        try {
            if (id == null || dto == null || dto.getId() == null || dto.getId() <= 0 || !id.equals(dto.getId()) || dto.getDeleteFlag() == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getNickname().equals(find.getCreateId())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자와 본인만 삭제 가능", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.boardCommentService.updateDeleteFlag(cudInfoDto, dto);
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
            IBoardComment find = this.boardCommentService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자 권한 필요", null);
            }
            Boolean result = this.boardCommentService.deleteById(id);
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
            IBoardComment result = this.getCommentAndLike(id, loginUser);
            if (result == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }
    @PostMapping("/findbyboardid")
    public ResponseEntity<ResponseDto> findByBoardId(Model model
            , @Validated @RequestBody SearchBoardCommentDto dto) {
        try {
            if ( dto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            List<BoardCommentDto> result = this.boardCommentService.findAllByBoardId(loginUser, dto);
            if ( result == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
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
            IBoardComment find = this.boardCommentService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardCommentService.addLikeQty(cudInfoDto, id);
            IBoardComment result = this.getCommentAndLike(id, loginUser);
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
            IBoardComment find = this.boardCommentService.findById(id);
            if (find == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember) model.getAttribute(SecurityConfig.LOGINUSER);
            if (loginUser == null) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardCommentService.subLikeQty(cudInfoDto, id);
            IBoardComment result = this.getCommentAndLike(id, loginUser);
            if (result == null) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    private IBoardComment getCommentAndLike(Long id, IMember loginUser) {
        IBoardComment result = this.boardCommentService.findById(id);
        if (result == null) {
            return null;
        }
        CommentLikeDto boardLikeDto = CommentLikeDto.builder()
                .commentTbl(new BoardCommentDto().getTbl())
                .nickname(loginUser.getNickname())
                .commentId(id)
                .build();
        Integer likeCount = this.commentLikeService.countByCommentTableUserBoard(boardLikeDto);
        result.setDeleteDt(likeCount.toString());
        return result;
    }
}

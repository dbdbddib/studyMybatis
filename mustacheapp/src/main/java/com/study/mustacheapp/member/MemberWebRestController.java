package com.study.mustacheapp.member;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.ResponseCode;
import com.study.mustacheapp.commons.dto.ResponseDto;
import com.study.mustacheapp.commons.inif.ICommonRestController;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
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
@RequestMapping("/api/v1/member")
public class MemberWebRestController implements ICommonRestController<MemberDto> {
    @Autowired
    private IMemberService memberService;

    @PostMapping
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestBody MemberDto dto) {
        try {
            if ( dto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자 권한 필요", null);
            }
            MemberDto memberDto = MemberDto.builder().userId(dto.getCreateId()).build();
            CUDInfoDto cudInfoDto = new CUDInfoDto(memberDto);
            IMember result = this.memberService.insert(cudInfoDto, dto);
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
    public ResponseEntity<ResponseDto> update(Model model, @PathVariable Long id, @Validated @RequestBody MemberDto dto) {
        try {
            if ( id == null || dto == null || !id.equals(dto.getId()) ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember find = this.memberService.findById(id);
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getCreateId().equals(find.getCreateId()) ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자와 본인만 수정 가능", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IMember result = this.memberService.update(cudInfoDto, dto);
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
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @PathVariable Long id, @Validated @RequestBody MemberDto dto) {
        try {
            if ( id == null || dto == null || !id.equals(dto.getId()) || dto.getDeleteFlag() == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember find = this.memberService.findById(id);
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자 권한 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            cudInfoDto.setDeleteInfo(dto);
            Boolean result = this.memberService.updateDeleteFlag(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember find = this.memberService.findById(id);
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            } else if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888889, "관리자 권한 필요", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.memberService.deleteById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            IMember find = this.memberService.findById(id);
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", find);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("/countName")  // POST method : /ct/countName
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            Integer result = this.memberService.countAllByNameContains(searchAjaxDto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            int total = this.memberService.countAllByNameContains(searchAjaxDto);
            List<IMember> list = this.memberService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "서버 검색 에러", null);
            }
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", searchAjaxDto);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }
}

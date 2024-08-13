package com.study.mustacheapp.board;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    @Qualifier("boardServiceImpl")
    @Autowired
    private IBoardService boardService;

    @PostMapping
    public ResponseEntity<IBoard> insert(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.insert(cudInfoDto, dto);
            if ( result == null ) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("")
    public ResponseEntity<IBoard> update(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.update(cudInfoDto, dto);
            if ( result == null ) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delflag")
    public ResponseEntity<Boolean> deleteFlag(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.boardService.deleteFlag(cudInfoDto, dto);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Boolean result = this.boardService.deleteById(id);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<IBoard> findById(@PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IBoard result = this.boardService.findById(id);
            this.boardService.addViewQty(id);
            if ( result == null ) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<SearchAjaxDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build();
            }
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            return ResponseEntity.ok(searchAjaxDto);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<Integer> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            return ResponseEntity.ok(total);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<String> addLikeQty(@PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            this.boardService.addLikeQty(id);
            return ResponseEntity.ok("OK");
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }
}

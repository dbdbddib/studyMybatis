package com.study.mustacheapp.sbfile;

import com.study.mustacheapp.commons.dto.ResponseCode;
import com.study.mustacheapp.commons.dto.ResponseDto;
import com.study.mustacheapp.commons.exeption.IdNotFoundException;
import com.study.mustacheapp.commons.exeption.LoginAccessException;
import com.study.mustacheapp.commons.inif.IResponseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sbfile")
public class SbFileWebRestController implements IResponseController {
    @Autowired
    private ISbFileService sbFileService;

    @PostMapping("/findbyboardid")
    public ResponseEntity<ResponseDto> findByBoardId(Model model
            , @Validated @RequestBody SbFileDto search) {
        try {
            if ( search == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            makeResponseCheckLogin(model);
            List<ISbFile> result = this.sbFileService.findAllByTblBoardId(search);
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

    @GetMapping(path = "/downloadfileid/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(Model model
            , @PathVariable Long id) {
        try{
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentLength(0).body(null);
            }
            makeResponseCheckLogin(model);
            ISbFile find = this.sbFileService.findById(id);
            byte[] bytes = this.sbFileService.getBytesFromFile(find);
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity
                    .ok()
                    .contentLength(bytes.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(find.getName(), StandardCharsets.UTF_8) + "\"")
                    .body(resource);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentLength(0).body(null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentLength(0).body(null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentLength(0).body(null);
        }
    }

    @GetMapping("/downloadfile/{tbl}/{name}/{uniqName}/{fileType}")
    public ResponseEntity<ByteArrayResource> downloadFile(Model model
            , @PathVariable String tbl, @PathVariable String name
            , @PathVariable String uniqName, @PathVariable String fileType) {
        try {
            if ( name == null || name.isEmpty() || uniqName == null || uniqName.isEmpty() || fileType == null || fileType.isEmpty() ) {
                return ResponseEntity.badRequest().build();
            }
            makeResponseCheckLogin(model);
            SbFileDto down = SbFileDto.builder()
                    .tbl(tbl).name(name).uniqName(uniqName).fileType(fileType).build();
            byte[] bytes = this.sbFileService.getBytesFromFile(down);
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity.ok()
                    .contentLength(bytes.length)
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8))
                    .body(resource);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentLength(0).body(null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentLength(0).body(null);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

//    @GetMapping("/downloadfileid/{id}")
//    public ResponseEntity<ByteArrayResource> downloadFileId(Model model
//            , @PathVariable Long id) {
//        try {
//            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
//            if ( loginUser == null ) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//            if ( id == null || id <= 0 ) {
//                return ResponseEntity.badRequest().build();
//            }
//            ISbFile find = this.sbFileService.findById(id);
//            if ( find == null ) {
//                return ResponseEntity.notFound().build();
//            }
//            byte[] bytes = this.sbFileService.getBytesFromFile(find);
//            ByteArrayResource resource = new ByteArrayResource(bytes);
//            return ResponseEntity.ok()
//                    .contentLength(bytes.length)
//                    .header("Content-Type", "application/octet-stream")
//                    .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(find.getName(), StandardCharsets.UTF_8))
//                    .body(resource);
//        } catch ( Exception ex ) {
//            log.error(ex.toString());
//            return ResponseEntity.internalServerError().build();
//        }
//    }
}

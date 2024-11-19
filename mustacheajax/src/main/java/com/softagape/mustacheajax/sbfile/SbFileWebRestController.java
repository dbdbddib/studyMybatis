package com.softagape.mustacheajax.sbfile;

import com.softagape.mustacheajax.commons.dto.ResponseCode;
import com.softagape.mustacheajax.commons.dto.ResponseDto;
import com.softagape.mustacheajax.commons.exeption.IdNotFoundException;
import com.softagape.mustacheajax.commons.exeption.LoginAccessException;
import com.softagape.mustacheajax.commons.inif.IResponseController;
import com.softagape.mustacheajax.filecntl.FileCtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/sbfile")
public class SbFileWebRestController implements IResponseController {
    @Autowired
    private ISbFileService sbFileService;
    @Autowired
    private FileCtrlService fileCtrlService;

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

    @PostMapping("/uploadSummernoteImageFile")
    public ResponseEntity<ResponseDto> uploadSummernoteImageFile(Model model, @RequestParam("file")MultipartFile file) {
        try {
            makeResponseCheckLogin(model);
            String fileName = file.getOriginalFilename();
            String extension = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
            String destFileName = String.valueOf(UUID.randomUUID() + extension);
            this.fileCtrlService.saveFile(file, "summernote", destFileName);
            String url = "/api/v1/sbfile/viewSummernoteImageFile/" + destFileName;
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", url);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentLength(0).body(null);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/viewSummernoteImageFile/{fileName}")
    public ResponseEntity<byte[]> viewSummernoteImageFile(Model model, @PathVariable String fileName) {
        try {
            makeResponseCheckLogin(model);
            String extension = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
            byte[] bytes = this.fileCtrlService.downloadFile("summernote", fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/" + extension);
//            headers.add("Content-Length", find.getLength().toString());
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentLength(0).body(null);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}

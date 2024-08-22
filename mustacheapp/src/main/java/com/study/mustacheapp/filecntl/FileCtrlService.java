package com.study.mustacheapp.filecntl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileCtrlService {
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    private void checkDirectory(String directory) throws IOException {
        Path path = Paths.get(directory);
        if ( !Files.exists(path) ) {
            Files.createDirectories(path);
        }
    }

    public Boolean saveFile(MultipartFile file, String tbl, String destFileName) throws IOException {
        if ( tbl == null || tbl.isEmpty() || file == null ) {
            return false;
        }
        this.checkDirectory(uploadDir + "/" + tbl);
        Files.copy( file.getInputStream(), Path.of(uploadDir + "/" + tbl + "/" + destFileName) );
        return true;
    }

    public byte[] downloadFile(String tbl, String uniqName, String fileType) {
        byte[] bytes = null;
        try {
            this.checkDirectory(uploadDir + "/" + tbl);
            Path path = Paths.get(uploadDir + "/" + tbl + "/" + uniqName + fileType);
            bytes = Files.readAllBytes(path);
        } catch (IOException ex) {
            log.error(ex.toString());
        }
        return bytes;
    }

    public Boolean deleteFile(String tbl, String uniqName, String fileType) {
        try {
            this.checkDirectory(uploadDir + "/" + tbl);
            Path path = Paths.get(uploadDir + "/" + tbl + "/" + uniqName + fileType);
            Files.delete(path);
        } catch (IOException ex) {
            log.error(ex.toString());
            return false;
        }
        return true;
    }
}

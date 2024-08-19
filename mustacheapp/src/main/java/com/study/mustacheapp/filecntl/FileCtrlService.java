package com.study.mustacheapp.filecntl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileCtrlService {
    private final String uploadDir = "Z:/home/files";

    public Boolean saveFile(MultipartFile file, String destFileName) throws IOException {
        if ( file == null ) {
            return false;
        }
        Files.copy( file.getInputStream(), Path.of(uploadDir + "/board/" + destFileName) );
        return true;
    }

    public Boolean saveFiles(MultipartFile[] files) throws IOException {
        if ( files == null || files.length <= 0 ) {
            return false;
        }
        for ( MultipartFile file : files ) {
            Files.copy( file.getInputStream()
                    , Path.of(uploadDir + "/" + file.getOriginalFilename()) );
        }
        return true;
    }

    public byte[] downloadFile(String tbl, String uniqName, String fileType) {
        byte[] bytes = null;
        try {
            Path path = Paths.get(uploadDir + "/" + tbl + "/" + uniqName + fileType);
            bytes = Files.readAllBytes(path);
        } catch (IOException ex) {
            log.error(ex.toString());
        }
        return bytes;
    }
}

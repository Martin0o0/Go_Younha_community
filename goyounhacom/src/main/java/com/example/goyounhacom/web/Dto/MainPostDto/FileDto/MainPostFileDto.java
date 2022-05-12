package com.example.goyounhacom.web.Dto.MainPostDto.FileDto;

import com.example.goyounhacom.domain.Photo.Photo;
import com.example.goyounhacom.domain.Users.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MainPostFileDto {
    private String originalFilename;
    private String filename;
    private String filePath;
    private String filetype;



    @Builder
    public MainPostFileDto(String origFilename, String filename, String filePath, String fileType) {
        this.originalFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.filetype = fileType;
    }


    public Photo toEntity() {
        return Photo.builder()
                .originalFilename(originalFilename)
                .filename(filename)
                .filePath(filePath)
                .filetype(filetype)
                .build();
    }

    @Override
    public String toString() {
        return "MainPostFileDto{" +
                "originalFilename='" + originalFilename + '\'' +
                ", filename='" + filename + '\'' +
                ", filePath='" + filePath + '\'' +
                ", filetype='" + filetype + '\'' +
                '}';
    }
}

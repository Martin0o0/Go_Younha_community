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



    @Builder
    public MainPostFileDto(String origFilename, String filename, String filePath) {
        this.originalFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }


    public Photo toEntity() {
        return Photo.builder()
                .originalFilename(originalFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
    }


    @Override
    public String toString() {
        return "MainPostFileDto{" +
                "originalFilename='" + originalFilename + '\'' +
                ", filename='" + filename + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

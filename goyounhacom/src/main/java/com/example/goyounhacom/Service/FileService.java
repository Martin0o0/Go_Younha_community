package com.example.goyounhacom.Service;

import com.example.goyounhacom.domain.Photo.Photo;
import com.example.goyounhacom.domain.Photo.PhotoRepository;
import com.example.goyounhacom.web.Dto.MainPostDto.FileDto.MainPostFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
    private final PhotoRepository photoRepository;

    @Transactional
    public Long saveFile(MainPostFileDto fileDto){
        log.info(fileDto.toString());
        return photoRepository.save(fileDto.toEntity()).getId();
    }


    @Transactional
    public MainPostFileDto getFile(Long id) {
        Photo file = photoRepository.findById(id).get();

        MainPostFileDto fileDto = MainPostFileDto.builder()
                .origFilename(file.getOriginalFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}



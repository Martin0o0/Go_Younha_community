package com.example.goyounhacom.domain.Photo;


import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFilename;

    @Column
    private String filename;

    @Column
    private String filePath;

    @Column
    private String filetype;



    @Builder
    public Photo(String originalFilename, String filename, String filePath, String filetype) {
        this.originalFilename = originalFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.filetype = filetype;
    }

}

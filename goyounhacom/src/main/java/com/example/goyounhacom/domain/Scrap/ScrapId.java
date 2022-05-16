package com.example.goyounhacom.domain.Scrap;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class ScrapId implements Serializable {

    private Long user;
    private Long mainPost;

    public ScrapId(){
    }

    public ScrapId(Long user, Long mainPost){
        super(); //기본생성자 생성 후 값 각각대입.
        this.user = user;
        this.mainPost = mainPost;
    }
}

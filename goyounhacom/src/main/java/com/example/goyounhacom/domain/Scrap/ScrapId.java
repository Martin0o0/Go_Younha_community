package com.example.goyounhacom.domain.Scrap;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class ScrapId implements Serializable { //직렬화 선언 필수이다. 이유는 상속받는 엔티티의 기본키 복합성 떄문이다.
    //즉, 다른 엔티티와 연관이 되어 있기에 복합 기본키를 사용할 때 사용.

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

package com.example.goyounhacom.domain.baseTimeEntity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속할 경우 필드들도 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) //기능 추가
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate; //최초 생성 시간

    @LastModifiedDate
    private LocalDateTime modifiedDate; //마지막 수정 시간

}


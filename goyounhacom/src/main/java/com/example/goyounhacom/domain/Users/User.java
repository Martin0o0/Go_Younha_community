package com.example.goyounhacom.domain.Users;


import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private Boolean is_holics;

    @Column(nullable = true)
    private Long no_holics;

    @Enumerated(EnumType.STRING) //Enum값은 어떤 형태로 저장할 지 결정.
    @Column(nullable = false)
    private Role role;



    @Builder
    public User(String username, String password, String email, String nickname, Boolean isholics, Long noholics, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.is_holics = isholics;
        this.no_holics = noholics;
        this.role = role;
    }


    public void update(String password, String email, String nickname){
        this.password = password;
        this.email = email;
        this.nickname = nickname;

    }

    public void setIs_holics(){
        this.is_holics = true;
        this.no_holics = this.getId();
    }

    public String getRoleKey(){
        return this.role.getKey();
    }


}

package com.example.goyounhacom.domain.Users;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByNickname(String nickname);

    Boolean existsByUsername(String username);

    Optional<User> findByNickname(String nickname);


    //Optional<User> findByJsonToken(String tkn);
}


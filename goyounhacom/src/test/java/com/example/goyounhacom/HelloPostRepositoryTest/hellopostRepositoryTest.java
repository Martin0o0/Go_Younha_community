package com.example.goyounhacom.HelloPostRepositoryTest;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class hellopostRepositoryTest {

    @Autowired
    HelloPostRepository helloPostRepository;

    @Test
    public void savetest(){
        //given
        String title = "고윤하 하위~";
        String content = "귀여운 깜찍이 고윤하 누님";

        helloPostRepository.save(HelloPost.builder()
                        .title(title)
                        .content(content)
                        .userId("chy0310")
                        .build());

        //when
        List<HelloPost> postsList = helloPostRepository.findAll();

        //then
        HelloPost posts = postsList.get(0);
        assertEquals(posts.getTitle(), "고윤하 하위~");
        assertEquals(posts.getContent(), "귀여운 깜찍이 고윤하 누님");
    }
    



}

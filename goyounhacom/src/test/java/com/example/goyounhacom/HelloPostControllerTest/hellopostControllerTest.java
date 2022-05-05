//package com.example.goyounhacom.HelloPostControllerTest;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.*;
//
//import com.example.goyounhacom.domain.HelloPosts.HelloPost;
//import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
//import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class hellopostControllerTest {
//
//    @LocalServerPort
//    int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private HelloPostRepository helloPostRepository;
//
//    @Test
//    public void controllerTest(){
//        //given
//        String title = "고윤하 하위~";
//        String content = "귀여운 깜찍이 고윤하 누님";
//
////        HelloPostsSaveDto helloPostsSaveDto = HelloPostsSaveDto.builder()
////                .title(title)
////                .content(content)
////                .user()
////                .build();
//
//        String url = "http://localhost:" + port + "/api/hellopost/save";
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, , Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<HelloPost> postsList = helloPostRepository.findAll();
//        HelloPost posts = postsList.get(0);
//        assertEquals(posts.getTitle(), "고윤하 하위~");
//        assertEquals(posts.getContent(), "귀여운 깜찍이 고윤하 누님");
//    }
//
//
//}

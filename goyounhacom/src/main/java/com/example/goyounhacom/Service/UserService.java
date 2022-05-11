package com.example.goyounhacom.Service;

import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MainPostRepository mainPostRepository;
    private final MainPostCommentRepository mainPostCommentRepository;
    private final HelloPostRepository helloPostRepository;
    private final CommentService commentService;
    private final HelloPostsService helloPostsService;
    private final MainPostsService mainPostsService;

    @Transactional
    public Long save(UserSaveDto userSaveDto) {
        if (userRepository.existsByUsername(userSaveDto.getUsername())) {
            return (long) -1;
        }
        else {
            String hashpw = passwordEncoder.encode(userSaveDto.getPassword());
            userSaveDto.setPassword(hashpw);
            return userRepository.save(userSaveDto.toEntity()).getId();
        }
    }

    @Transactional
    public Long update(Long id, UserUpdateDto userUpdateDto) { //유저정보 업데이트
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없음"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getNickname());
        return user.getId();
    }

    @Transactional
    public Long updateUser(UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDatails principalDatails){
        User user = userRepository.findByUsername(userUpdateDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없음"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getNickname());
        principalDatails.setUser(user); //세션유지를 위해 User정보를 의존성 주입으로 넣어준다.
        return user.getId();
    }



    @Transactional
    public List<UserGetDto> findByAll() { //전체조회
        return userRepository.findAll().stream().map(dto -> new UserGetDto(dto)).collect(Collectors.toList());
    }

    @Transactional
    public Long updateholics(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없음"));
        user.setIs_holics();
        return id;
    }

    @Transactional
    public Long updateAdmin(String username){
        User entity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당하는 유저 아이디는 없습니다. " + username));
        entity.setRoleAdmin();
        return entity.getId();
    }

    @Transactional
    public UserGetDto findbyid(Long No) { //회원번호로 조회
        User entity = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 유저는 없습니다. No : " + No));

        return new UserGetDto(entity);
    }

    @Transactional
    public UserGetDto findbyusername(String userid) {//회원아이디로 조회
        User entity = userRepository.findByUsername(userid).orElseThrow(() -> new IllegalArgumentException("해당하는 유저 아이디는 없습니다. " + userid));
        return new UserGetDto(entity);
    }

    @Transactional
    public User getbyUsername(String username){
        User entity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당하는 유저 아이디는 없습니다. " + username));
        return entity;
    }

    @Transactional
    public void deletebyid(Long No) { //회원번호로 삭제
        User user = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 회원번호가 없다. 삭제할 수 없음."));
        //log.info("boolean ? {}", mainPostCommentRepository.existsByUser(user));
        if(mainPostCommentRepository.existsByUser(user)){ //이친구가 쓴 댓글부터 지우고
            List<MainPostComment> list = mainPostCommentRepository.findByUser(user);
            for(int i = 0; i < list.size(); i++){
                commentService.delete(list.get(i).getId());
//                log.info("댓글 리스트 : {}", list.get(i).toString());
            }
        }

        if(helloPostRepository.existsByUser(user)){ //등업 게시판 지우고.
            HelloPost helloPost = helloPostRepository.findByUser(user);
            helloPostsService.deletebyid(helloPost.getId());
        }

        if(mainPostRepository.existsById(user.getId())){ //자유게시글 지우고
            List<MainPost> list = mainPostRepository.findByUser(user);
            for(int i = 0; i < list.size(); i++){
                mainPostsService.delete(list.get(i).getId());
            }
        }

      userRepository.delete(user);
    }







}

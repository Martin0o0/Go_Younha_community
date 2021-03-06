package com.example.goyounhacom.Service;

import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
import com.example.goyounhacom.domain.MainPostLike.MainPostLike;
import com.example.goyounhacom.domain.MainPostLike.MainPostLikeRepository;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.domain.Scrap.Scrap;
import com.example.goyounhacom.domain.Scrap.ScrapRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ScrapRepository scrapRepository;

    private final MainPostLikeRepository mainPostLikeRepository;

    @Transactional
    public Long save(UserSaveDto userSaveDto) {
        if (userRepository.existsByUsername(userSaveDto.getUsername())) {
            return (long) -1;
        }
        else if(userRepository.existsByNickname(userSaveDto.getNickname())){
            return (long) -2;
        }
        else {
            String hashpw = passwordEncoder.encode(userSaveDto.getPassword());
            userSaveDto.setPassword(hashpw);
            return userRepository.save(userSaveDto.toEntity()).getId();
        }
    }

    @Transactional
    public Long update(Long id, UserUpdateDto userUpdateDto) { //???????????? ????????????
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ??????"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getNickname());
        return user.getId();
    }

    @Transactional
    public Long updateUser(UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDatails principalDatails){
        User user = userRepository.findByUsername(userUpdateDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ??????"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getNickname());
        principalDatails.setUser(user); //??????????????? ?????? User????????? ????????? ???????????? ????????????.
        return user.getId();
    }



    @Transactional
    public List<UserGetDto> findByAll() { //????????????
        return userRepository.findAll().stream().map(dto -> new UserGetDto(dto)).collect(Collectors.toList());
    }

    @Transactional
    public Long updateholics(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ??????"));
        user.setIs_holics();
        return id;
    }

    @Transactional
    public Long updateAdmin(String username){
        User entity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????? ????????????. " + username));
        entity.setRoleAdmin();
        return entity.getId();
    }

    @Transactional
    public UserGetDto findbyid(Long No) { //??????????????? ??????
        User entity = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ????????????. No : " + No));

        return new UserGetDto(entity);
    }

    @Transactional
    public UserGetDto findbyusername(String userid) {//?????????????????? ??????
        User entity = userRepository.findByUsername(userid).orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????? ????????????. " + userid));
        return new UserGetDto(entity);
    }

    @Transactional
    public UserGetDto findbynickname(String nickname){
        User entity = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("???????????? ???????????? ?????? ????????? ????????????.."));
        return new UserGetDto(entity);
    }

    @Transactional
    public User getbyUsername(String username){
        User entity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????? ????????????. " + username));
        return entity;
    }

    @Transactional
    public void deletebyid(Long No) { //??????????????? ??????
        User user = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("???????????? ??????????????? ??????. ????????? ??? ??????."));
        //log.info("boolean ? {}", mainPostCommentRepository.existsByUser(user));
        if(mainPostCommentRepository.existsByUser(user)){ //???????????? ??? ???????????? ?????????
            List<MainPostComment> list = mainPostCommentRepository.findByUser(user);
            for(int i = 0; i < list.size(); i++){
                commentService.delete(list.get(i).getId());
//                log.info("?????? ????????? : {}", list.get(i).toString());
            }
        }

        if(helloPostRepository.existsByUser(user)){ //?????? ????????? ?????????.
            HelloPost helloPost = helloPostRepository.findByUser(user);
            helloPostsService.deletebyid(helloPost.getId());
        }

        if(mainPostRepository.existsById(user.getId())){ //??????????????? ?????????
            List<MainPost> list = mainPostRepository.findByUser(user);
            for(int i = 0; i < list.size(); i++){
                mainPostsService.delete(list.get(i).getId());
            }
        }

        if(scrapRepository.existsByUserId(user.getId())){ //??????????????? ?????????????????? ??????.
            List<Scrap> list = scrapRepository.findAllByUserId(user.getId(), null).getContent();
            for(int i = 0; i < list.size(); i++){
                scrapRepository.delete(list.get(i));
            }
        }
        if(mainPostLikeRepository.existsByUserId(user.getId())){
            List<MainPostLike> list = mainPostLikeRepository.findAllByUserId(user.getId());
            for(MainPostLike i : list){
                mainPostLikeRepository.delete(i);
            }
        }


      userRepository.delete(user);
    }







}

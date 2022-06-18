package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.MainPostLike.MainPostLike;
import com.example.goyounhacom.domain.MainPostLike.MainPostLikeRepository;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainPostLikeService {

    private final MainPostLikeRepository mainPostLikeRepository;

    private final UserService userService;

    private final MainPostsService mainPostsService;

    @Transactional
    public Boolean addordeleteLike(User user, Long postid){
        MainPost mainPost = mainPostsService.getMainPostId(postid);
        //만일 이미 좋아요가 존재하지 않는다면,
        if(mainPostLikeRepository.existsByUserIdAndMainPostId(user.getId(), postid) == false){
            MainPostLike mainPostLike = new MainPostLike(user, mainPost);
            mainPostLikeRepository.save(mainPostLike);
            log.info("{}", "추천 저장됨");
            return true;
        }
        else{
            MainPostLike mainPostLike = mainPostLikeRepository.findByUserIdAndMainPostId(user.getId(), postid).get();
            mainPostLikeRepository.delete(mainPostLike);
            log.info("{}", "추천 취소됨");
            return false;
        }
    }

    @Transactional
    public long getCountLike(Long postid){
        if(mainPostLikeRepository.countByMainPostId(postid).isPresent()){
            return mainPostLikeRepository.countByMainPostId(postid).get();
        }
        else{
            return 0;
        }
    }

    public Boolean existLike(Long userid,Long postid){
        return mainPostLikeRepository.existsByUserIdAndMainPostId(userid, postid);
    }


    public boolean existMainPostlike(Long postid){
        return mainPostLikeRepository.existsByMainPostId(postid);
    }


}

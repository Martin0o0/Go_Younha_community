package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.Infomation.NoticeRepository;
import com.example.goyounhacom.web.Dto.Imformation.NoticeSaveDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(NoticeSaveDto noticeSaveDto) {
        return noticeRepository.save(noticeSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, NoticeSaveDto noticeSaveDto) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        return noticeRepository.save(noticeSaveDto.toEntity()).getId();
    }


    @Transactional
    public Long delete(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 공지사항이 없습니다."));
        noticeRepository.delete(notice);
        return id;
    }

    @Transactional(readOnly = true)
    public Page<Notice> pagelist(Pageable pageable){
        Page<Notice> list = noticeRepository.findAll(pageable);
        return list;
    }


}

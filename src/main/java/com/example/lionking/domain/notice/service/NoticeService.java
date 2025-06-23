package com.example.lionking.domain.notice.service;

import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.domain.media.dto.MediaRequest;
import com.example.lionking.domain.media.dto.MediaResponse;
import com.example.lionking.domain.media.entity.MediaOwner;
import com.example.lionking.domain.media.service.MediaService;
import com.example.lionking.domain.notice.dto.NoticeRequest;
import com.example.lionking.domain.notice.dto.NoticeResponse;
import com.example.lionking.domain.notice.entity.Notice;
import com.example.lionking.domain.notice.repository.NoticeRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final MediaService mediaService;

    @Transactional
    public NoticeResponse postNotice(NoticeRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 유저입니다."));

        Notice notice = Notice.builder()
                .noticeType(request.noticeType())
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
        noticeRepository.save(notice);

        List<MediaRequest> mediaRequests = request.contentMedia().stream()
                .map(media -> new MediaRequest(
                        media.s3Key(),
                        media.mediaType(),
                        MediaOwner.NOTICE,
                        notice.getId()
                )).toList();
        mediaService.saveAll(mediaRequests);

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.NOTICE, notice.getId());
        return NoticeResponse.from(notice, mediaList);
    }

    @Transactional(readOnly = true)
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> {
                    List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.NOTICE, notice.getId());
                    return NoticeResponse.from(notice, mediaList);
                }).toList();
    }

    @Transactional(readOnly = true)
    public NoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 공지입니다."));

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.NOTICE, notice.getId());
        return NoticeResponse.from(notice, mediaList);
    }

    @Transactional
    public NoticeResponse updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 공지입니다."));

        mediaService.replaceAll(MediaOwner.NOTICE, notice.getId(),
                request.contentMedia().stream()
                        .map(media -> new MediaRequest(
                                media.s3Key(),
                                media.mediaType(),
                                MediaOwner.NOTICE,
                                notice.getId()))
                        .toList());

        notice.update(request.title(), request.content());
        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.NOTICE, notice.getId());
        return NoticeResponse.from(notice, mediaList);
    }

    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 공지입니다."));
        mediaService.deleteAll(MediaOwner.NOTICE, notice.getId());
        noticeRepository.delete(notice);
    }
}

package com.example.lionking.domain.activity.service;

import com.example.lionking.domain.activity.dto.ActivityRequest;
import com.example.lionking.domain.activity.dto.ActivityResponse;
import com.example.lionking.domain.activity.entity.Activity;
import com.example.lionking.domain.activity.repository.ActivityRepository;
import com.example.lionking.domain.media.dto.MediaRequest;
import com.example.lionking.domain.media.dto.MediaResponse;
import com.example.lionking.domain.media.entity.MediaOwner;
import com.example.lionking.domain.media.service.MediaService;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final MemberRepository memberRepository;
    private final MediaService mediaService;

    @Transactional
    public ActivityResponse postActivity(ActivityRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 유저입니다."));

        Activity activity = Activity.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
        activityRepository.save(activity);

        List<MediaRequest> mediaRequests = request.contentMedia().stream()
                .map(media -> new MediaRequest(
                        media.s3Key(),
                        media.mediaType(),
                        MediaOwner.ACTIVITY,
                        activity.getId()
                )).toList();
        mediaService.saveAll(mediaRequests);

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.ACTIVITY, activity.getId());
        return ActivityResponse.from(activity, mediaList);
    }

    @Transactional(readOnly = true)
    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activity -> ActivityResponse.from(
                        activity,
                        mediaService.findAll(MediaOwner.ACTIVITY, activity.getId())
                )).toList();
    }

    @Transactional(readOnly = true)
    public ActivityResponse getActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 활동기록입니다."));

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.ACTIVITY, activity.getId());
        return ActivityResponse.from(activity, mediaList);
    }

    @Transactional
    public ActivityResponse updateActivity(Long activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 활동기록입니다."));

        mediaService.replaceAll(MediaOwner.ACTIVITY, activity.getId(), request.contentMedia().stream()
                .map(media -> new MediaRequest(
                        media.s3Key(),
                        media.mediaType(),
                        MediaOwner.ACTIVITY,
                        activity.getId()
                )).toList());

        activity.update(request.title(), request.content());

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.ACTIVITY, activity.getId());
        return ActivityResponse.from(activity, mediaList);
    }

    @Transactional
    public void deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 활동기록입니다."));

        mediaService.deleteAll(MediaOwner.ACTIVITY, activity.getId());
        activityRepository.delete(activity);
    }
}

package com.example.lionking.domain.media.service;

import com.example.lionking.domain.media.dto.MediaRequest;
import com.example.lionking.domain.media.dto.MediaResponse;
import com.example.lionking.domain.media.entity.Media;
import com.example.lionking.domain.media.entity.MediaOwner;
import com.example.lionking.domain.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public void saveAll(List<MediaRequest> requests) {
        for (MediaRequest request : requests) {
            mediaRepository.save(Media.builder()
                    .s3Key(request.s3Key())
                    .mediaType(request.mediaType())
                    .mediaOwner(request.mediaOwner())
                    .ownerId(request.ownerId())
                    .build());
        }
    }

    public List<MediaResponse> findAll(MediaOwner owner, Long ownerId) {
        return mediaRepository.findByMediaOwnerAndOwnerId(owner, ownerId).stream()
                .map(media -> new MediaResponse(media.getId(), media.getS3Key(), media.getMediaType()))
                .toList();
    }

    @Transactional
    public void deleteAll(MediaOwner owner, Long ownerId) {
        mediaRepository.deleteByMediaOwnerAndOwnerId(owner, ownerId);
    }

    @Transactional
    public void replaceAll(MediaOwner owner, Long ownerId, List<MediaRequest> newRequests) {
        deleteAll(owner, ownerId);
        saveAll(newRequests);
    }

}
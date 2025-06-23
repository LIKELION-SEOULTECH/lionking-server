package com.example.lionking.domain.media.repository;

import com.example.lionking.domain.media.entity.Media;
import com.example.lionking.domain.media.entity.MediaOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByMediaOwnerAndOwnerId(MediaOwner mediaOwner, Long ownerId);

    void deleteByMediaOwnerAndOwnerId(MediaOwner mediaOwner, Long ownerId);
}

package com.example.lionking.domain.activity.repository;

import com.example.lionking.domain.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}

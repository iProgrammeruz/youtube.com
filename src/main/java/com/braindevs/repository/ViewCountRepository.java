package com.braindevs.repository;

import com.braindevs.entity.ViewCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewCountRepository extends JpaRepository<ViewCountEntity, Integer> {
}

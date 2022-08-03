package com.ssafy.db.repository;

import com.ssafy.db.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    List<Projects> findProjectsByOwnerId(Long ownerId);
    Optional<List<Projects>> findProjectsByOwnerIdAndActivation(Long ownerId, boolean activate);
}

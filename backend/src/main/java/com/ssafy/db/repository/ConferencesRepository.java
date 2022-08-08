package com.ssafy.db.repository;

import com.ssafy.db.entity.Conferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferencesRepository extends JpaRepository<Conferences, Long> {
}

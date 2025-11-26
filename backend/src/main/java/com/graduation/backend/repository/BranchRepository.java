package com.graduation.backend.repository;

import com.graduation.backend.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByName(String name);
}

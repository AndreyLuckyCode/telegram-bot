package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {
        ApprovalStatus findBySearcherIdAndEmployerId(Long searcherId, Long employerId);
    }


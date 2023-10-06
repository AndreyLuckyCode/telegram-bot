package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.ApprovalStatusRepository;
import com.andrey.lucky_job.models.ApprovalStatus;
import org.springframework.stereotype.Service;

@Service
public class ApprovalStatusService {

    private ApprovalStatusRepository repository;

    public ApprovalStatusService(ApprovalStatusRepository repository) {
        this.repository = repository;
    }

    public ApprovalStatus findBySearcherIdAndEmployerId(Long searcherId, Long employerId) {
        return repository.findBySearcherIdAndEmployerId(searcherId, employerId);
    }

    public ApprovalStatus save(ApprovalStatus status) {
        return repository.save(status);
    }
}
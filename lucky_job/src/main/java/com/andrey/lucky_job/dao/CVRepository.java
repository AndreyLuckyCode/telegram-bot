package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CVRepository extends JpaRepository<CV, Long> {
    @Transactional
    void deleteAllCVByVacancyId(Long vacancyId);
    List<CV> findByVacancyId(Long vacancyId);
}

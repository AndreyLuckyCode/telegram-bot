package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CVRepository extends JpaRepository<CV, Long> {
    @Transactional
    void deleteAllCVByVacancyId(Long vacancyId);
}

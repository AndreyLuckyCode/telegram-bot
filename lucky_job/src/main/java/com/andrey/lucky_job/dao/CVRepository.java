package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.CV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVRepository extends JpaRepository<CV, Long> {
}

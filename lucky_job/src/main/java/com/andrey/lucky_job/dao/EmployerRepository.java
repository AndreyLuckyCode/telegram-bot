package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}

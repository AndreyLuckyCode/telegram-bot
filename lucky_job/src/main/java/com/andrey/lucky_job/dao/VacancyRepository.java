package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}

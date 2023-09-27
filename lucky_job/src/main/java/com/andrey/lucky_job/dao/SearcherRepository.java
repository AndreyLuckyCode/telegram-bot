package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearcherRepository extends JpaRepository<Searcher, Long> {
        Searcher findByEmailAndPassword(String email, String password);
        boolean existsByPassword(String password);
        boolean existsByEmail(String email);
}

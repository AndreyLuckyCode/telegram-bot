package com.andrey.lucky_job.dao;

import com.andrey.lucky_job.models.Searcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearcherRepository extends JpaRepository<Searcher, Long> {
}

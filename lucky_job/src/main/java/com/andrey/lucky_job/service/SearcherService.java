package com.andrey.lucky_job.service;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SearcherService {

    public List<Searcher> getAllSearchers();
    @Transactional
    public boolean saveSearcher(Searcher searcher);
    public Searcher getSearcher(Long id);
    public void deleteSearcher(Long id);
    public Searcher findSearcherByEmailAndPassword(String email, String password);
    public boolean isPasswordUnique(String password);
    public boolean isEmailUnique(String email);
    Searcher findSearcherByEmail(String email);
}

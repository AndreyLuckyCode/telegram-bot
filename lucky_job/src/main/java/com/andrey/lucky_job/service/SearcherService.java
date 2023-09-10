package com.andrey.lucky_job.service;

import com.andrey.lucky_job.models.Searcher;

import java.util.List;

public interface SearcherService {

    public List<Searcher> getAllSearchers();
    public void saveSearcher(Searcher searcher);
    public Searcher getSearcher(Long id);
    public void deleteSearcher(Long id);
}

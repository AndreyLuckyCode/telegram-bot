package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.SearcherRepository;
import com.andrey.lucky_job.models.Searcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SearcherServiceImpl implements SearcherService{

    @Autowired
    private SearcherRepository searcherRepository;

    @Override
    public List<Searcher> getAllSearchers() {
        return searcherRepository.findAll();
    }
    @Override
    public void saveSearcher(Searcher searcher) {
        searcherRepository.save(searcher);
    }
    @Override
    public Searcher getSearcher(Long id) {
        Searcher searcher = null;
        Optional<Searcher> optional = searcherRepository.findById(id);
            if(optional.isPresent()){searcher = optional.get();}

        return searcher;
    }
    @Override
    public void deleteSearcher(Long id) {
        searcherRepository.deleteById(id);
    }

}

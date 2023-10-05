package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.SearcherRepository;
import com.andrey.lucky_job.models.Searcher;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearcherServiceImpl implements SearcherService{

    @Autowired
    private SearcherRepository searcherRepository;

    @Override
    public List<Searcher> getAllSearchers() {
        return searcherRepository.findAll();
    }
    public boolean saveSearcher(Searcher searcher) {
        try {
            searcherRepository.save(searcher);
            return true;
        } catch (Exception e) {
            Notification.show("Error");
            return false;
        }
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
    @Override  //Для проверки данных при авторизации
    public Searcher findSearcherByEmailAndPassword(String email, String password) {
        return searcherRepository.findByEmailAndPassword(email, password);
    }
    @Override  // Для проверки уникальности при регистрации
    public boolean isPasswordUnique(String password) {
        return !searcherRepository.existsByPassword(password);
    }
    @Override  // Для проверки при регистрации
    public boolean isEmailUnique(String email) {
        return !searcherRepository.existsByEmail(email);
    }
    @Override  // Для поиска автора резюме (CV)
    public Searcher findSearcherByEmail(String email) {
        return searcherRepository.findByEmail(email);
    }
}


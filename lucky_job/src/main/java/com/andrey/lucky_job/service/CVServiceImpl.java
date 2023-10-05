package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.CVRepository;
import com.andrey.lucky_job.dao.VacancyRepository;
import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CVServiceImpl implements CVService{

    @Autowired
    public CVRepository cvRepository;
    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public List<CV> getAllCVs() {
        return cvRepository.findAll();
    }
    @Override
    public void saveCV(CV cv) {
        cvRepository.save(cv);

    }
    @Override
    public CV getCV(Long id) {
        CV cv = null;
        Optional<CV> optional = cvRepository.findById(id);
            if(optional.isPresent()){cv = optional.get();}

        return cv;
    }
    @Override
    public void deleteCV(Long id) {
        cvRepository.deleteById(id);
    }
    @Override  // Для делита всех резюме каскадом при делите вакансии
    public void deleteAllCVByVacancyId(Long vacancyId) {
        cvRepository.deleteAllCVByVacancyId(vacancyId);
    }
    @Override  // Для поиска всех резюме под вакансией
    public List<CV> getCVsForVacancy(Long vacancyId) {
        return cvRepository.findByVacancyId(vacancyId);
    }
    @Override  // Фильтрация по лайкам
    public List<CV> findLikedCVsByAuthor(String author) {
        return cvRepository.findByAuthorAndLikedIsTrue(author);
    }
    @Override  // Поиск всех резюме под всеми вакансиями определенного работодателя
    public List<CV> findAllCVsByEmployerId(Long employerId) {
        // Получаем список всех вакансий, связанных с работодателем
        List<Vacancy> vacancies = vacancyRepository.findAllByEmployerId(employerId);

        // Инициализируем список для хранения связанных резюме
        List<CV> cvs = new ArrayList<>();

        // Цикл через все вакансии
        for (Vacancy vacancy : vacancies) {
            // Добавляем все резюме, связанные с каждой вакансией
            cvs.addAll(cvRepository.findByVacancyId(vacancy.getId()));
        }

        // Возвращаем список резюме
        return cvs;
    }

}

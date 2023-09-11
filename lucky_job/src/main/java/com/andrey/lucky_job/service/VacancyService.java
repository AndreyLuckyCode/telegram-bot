package com.andrey.lucky_job.service;

import com.andrey.lucky_job.models.Vacancy;

import java.util.List;

public interface VacancyService {

    public List<Vacancy> getAllVacancies();
    public void saveVacancy(Vacancy vacancy);
    public Vacancy getVacancy(Long id);
    public void deleteVacancy(Long id);
}

package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.CVRepository;
import com.andrey.lucky_job.models.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CVServiceImpl implements CVService{

    @Autowired
    public CVRepository cvRepository;

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
    @Override
    public void deleteAllCVByVacancyId(Long vacancyId) {
        cvRepository.deleteAllCVByVacancyId(vacancyId);
    }
    @Override
    public List<CV> getCVsForVacancy(Long vacancyId) {
        return cvRepository.findByVacancyId(vacancyId);
    }
}

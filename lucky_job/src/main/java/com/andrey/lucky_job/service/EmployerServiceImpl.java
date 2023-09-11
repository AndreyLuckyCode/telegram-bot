package com.andrey.lucky_job.service;

import com.andrey.lucky_job.dao.EmployerRepository;
import com.andrey.lucky_job.models.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }
    @Override
    public void saveEmployer(Employer employer) {
        employerRepository.save(employer);
    }
    @Override
    public Employer getEmployer(Long id) {
        Employer employer = null;
        Optional<Employer> optional = employerRepository.findById(id);
            if(optional.isPresent()){employer = optional.get();}

        return employer;
    }
    @Override
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}

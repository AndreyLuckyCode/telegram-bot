package com.andrey.lucky_job.service;

import com.andrey.lucky_job.models.Employer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployerService {
    public List<Employer> getAllEmployers();
    @Transactional
    public boolean saveEmployer(Employer employer);
    public Employer getEmployer(Long id);
    public void deleteEmployer(Long id);
//    public Employer findEmployerByEmailAndPassword(String email, String password);
    public boolean isPasswordUnique(String password);
    public boolean isEmailUnique(String email);
    public Employer findEmployerByEmail(String email);
}

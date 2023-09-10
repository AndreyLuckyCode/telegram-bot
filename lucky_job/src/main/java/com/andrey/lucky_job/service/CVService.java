package com.andrey.lucky_job.service;

import com.andrey.lucky_job.models.CV;
import java.util.List;

public interface CVService {

    public List<CV> getAllCVs();
    public void saveCV(CV cv);
    public CV getCV(Long id);
    public void deleteCV(Long id);
}

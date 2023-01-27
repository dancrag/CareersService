package com.by.CareersService.Services;

import com.by.CareersService.Model.JobProfile;
import com.by.CareersService.Repository.JobProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobProfileService {

    @Autowired
    JobProfileRepository jobProfileRepository;

    @Cacheable("jobProfile")
    public JobProfile findJobProfile(String jobId) {

        Optional<JobProfile> jobProfile = jobProfileRepository.findJobProfileById(jobId);

        if(jobProfile.isEmpty()) {
            return null;
        }

        return jobProfile.get();
    }
}

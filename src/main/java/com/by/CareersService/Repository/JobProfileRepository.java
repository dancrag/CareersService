package com.by.CareersService.Repository;

import com.by.CareersService.Model.JobProfile;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobProfileRepository extends CrudRepository<JobProfile,String> {

    @Query("SELECT * FROM job WHERE job_id = :jobId AND java_exp >= :javaExp AND spring_exp >= :springExp ALLOW FILTERING")
    Optional<JobProfile> findByJobIdAndJavaExpAndSpringExp(@Param(value = "jobId") String job_id, @Param(value = "javaExp") Double java_exp, @Param(value = "springExp") Double spring_exp);

    @Query("SELECT * FROM job WHERE job_id = :jobId ALLOW FILTERING")
    Optional<JobProfile> findJobProfileById(@Param(value = "jobId") String job_id);
}

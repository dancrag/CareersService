package com.by.CareersService.Controller;

import com.by.CareersService.Model.Employee;
import com.by.CareersService.Model.JobProfile;
import com.by.CareersService.Model.JobRequest;
import com.by.CareersService.Repository.JobProfileRepository;
import com.by.CareersService.Services.JobProfileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/")
@Slf4j
public class JobController {

    @Autowired
    JobProfileRepository jobProfileRepository;

    @Autowired
    JobProfileService jobProfileService;

    @PostMapping("/createJobProfile")
    public ResponseEntity<JobProfile> createJobProfile(@RequestBody JobProfile jobProfile) {
        try {

            if (jobProfileRepository.findByJobIdAndJavaExpAndSpringExp(jobProfile.getJob_id(), jobProfile.getJava_exp(), jobProfile.getSpring_exp()).isPresent()) {
                return new ResponseEntity<>( new JobProfile(
                        jobProfile.getJob_id(),
                        jobProfile.getJob_name(),
                        jobProfile.getJava_exp(),
                        jobProfile.getSpring_exp(),
                        "Already Exists"
                ), HttpStatus.OK);
            }

            JobProfile newJobProfileCreated = jobProfileRepository.save( new JobProfile(
                    jobProfile.getJob_id(),
                    jobProfile.getJob_name(),
                    jobProfile.getJava_exp(),
                    jobProfile.getSpring_exp(),
                    "Created"
            ));
            return new ResponseEntity<>(newJobProfileCreated, HttpStatus.OK);

        } catch (Exception exception) {

            log.error("exception encountered: {}", exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findEmpForJobId")
    public ResponseEntity<List<Employee>> findEmpForJobId(@RequestBody JobRequest jobRequest) throws IOException, ExecutionException, InterruptedException {

        try {

            Optional<JobProfile> jobProfile = jobProfileRepository.findJobProfileById(jobRequest.getJob_id());

            if (jobProfile.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            JobProfile jobProfileFound = jobProfile.get();

            HttpClient client = HttpClient.newHttpClient();

            ObjectMapper mapper = new ObjectMapper();

            String jsonString = mapper.writeValueAsString(jobProfileFound);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/findEmpSkillset"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-type", "application/json")
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response = futureResponse.get();

            List<Employee> employeeList = mapper.readValue(response.body(), new TypeReference<>(){});

            return new ResponseEntity<>(employeeList, HttpStatus.OK);

        } catch (Exception e) {

            System.out.println(e);
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/getJobProfileFromCache")
    public ResponseEntity<JobProfile> getJobProfileFromCache(@RequestBody JobRequest jobRequest) {

        JobProfile jobProfile = jobProfileService.findJobProfile(jobRequest.getJob_id());

        if (jobProfile.equals(null)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jobProfile, HttpStatus.OK);
    }
}

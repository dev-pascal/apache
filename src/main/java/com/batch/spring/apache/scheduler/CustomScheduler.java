package com.batch.spring.apache.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;

public class CustomScheduler {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	@PostConstruct
	public void schedule() throws Exception {
        final JobParameters params = new JobParametersBuilder() 
                .addString("exampleJob", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
	}
	
}

package com.batch.spring.apache.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.batch.spring.apache.model.Model;
import com.batch.spring.apache.scheduler.CustomScheduler;
import com.batch.spring.apache.writer.CustomItemWriter;

@Configuration
public class SpringBatchConfiguration {

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private static final Integer skipLine = 1;
	private static final String directory = "example.xlsx";
	
	@Bean
	public Job exampleJob(
			final Step dataMigrationStep,
			final Step markExcelAsReadStep) {
		return new JobBuilder("exampleJob", jobRepository)
				.start(markExcelAsReadStep)
				.build();
	}
	
	@Bean
	public Step markExcelAsReadStep(
			final ItemReader<Model> reader,
			final CustomItemWriter writer) {
		return new StepBuilder("markExcelAsReadStep", jobRepository)
				.<Model, Model>chunk(1, transactionManager)
				.reader(reader)
				.writer(writer)
				.build();
	}

	@Bean
	public ItemReader<Model> itemReader() {
		final var reader = new PoiItemReader<Model>();
		
		reader.setResource(new FileSystemResource(directory));
		reader.setRowMapper(excelRowMapper());
		reader.setLinesToSkip(skipLine);
		reader.setMaxItemCount(1);
		
		return reader;
	}

	@Bean
	public CustomItemWriter customItemWriter() {
		return new CustomItemWriter();
	}
	
	@Bean
	public RowMapper<Model> excelRowMapper() {
		final var mapper = new BeanWrapperRowMapper<Model>();
		mapper.setTargetType(Model.class);
		return mapper;
	}
	
	@Bean
	public CustomScheduler customScheduler() {
		return new CustomScheduler();
	}
	
}

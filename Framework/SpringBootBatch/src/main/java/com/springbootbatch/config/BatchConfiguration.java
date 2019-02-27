package com.springbootbatch.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.springbootbatch.batchsteps.CustomItemProcessor;
import com.springbootbatch.model.User;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String dbdriver;

	@Value("${spring.datasource.username}")
	private String dbun;

	@Value("${spring.datasource.password}")
	private String dbpwd;

	@Autowired
	public MongoTemplate mongoTemplate;

	@Bean
	public DataSource dataSource() throws SQLException {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbdriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbun);
		dataSource.setPassword(dbpwd);
		return dataSource;
	}

	@Bean
	public JdbcCursorItemReader<User> reader() {
		JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
		try {
			reader.setDataSource(dataSource());
			reader.setSql("SELECT id,name FROM user");
			reader.setRowMapper(new UserRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}

	public class UserRowMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			return user;
		}

	}

	@Bean
	public CustomItemProcessor processor() {
		return new CustomItemProcessor();
	}

	@Bean
	public MongoItemWriter<User> mangoDbWriter() {
		MongoItemWriter<User> writer = new MongoItemWriter<User>();
		writer.setTemplate(mongoTemplate);
		writer.setCollection("user");
		return writer;
	}

	@Bean
	public FlatFileItemWriter<User> writer() {
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
		writer.setResource(new ClassPathResource("user.csv"));
		System.out.println(writer);
		writer.setLineAggregator(new DelimitedLineAggregator<User>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
					{
						System.out.println("comming instaide");
						setNames(new String[] { "id", "name" });
					}
				});
			}
		});

		return writer;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<User, User> chunk(10).reader(reader()).processor(processor())
				/* .writer(writer()) */
				.writer(mangoDbWriter()).build();
	}

	@Bean
	public Job exportUserJob() {
		return jobBuilderFactory.get("exportUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

}

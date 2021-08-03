package com.example.mongodemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MongodemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address("Turkey","Gaziantep","27000");

			String email = "nacarct@gmail.com";

			Student student = new Student(
					"Temuçin","Nacar",email,
					Gender.MALE,address,List.of("Software Development","Video Games"), BigDecimal.TEN, LocalDateTime.now()
			);

			//usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);

			//studentRepository.findStudentByEmail(email).ifPresentOrElse(s -> {System.out.println(s + " already exist.");},()->{ System.out.println("inserting student : " + student);studentRepository.insert(student); });
		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(email));


		List<Student> students = mongoTemplate.find(query,Student.class);

		if (students.size()>1)
			throw new IllegalStateException("found many students with email "+ email);

		if (students.isEmpty()){
			System.out.println("inserting student : " + student);
			studentRepository.insert(student);
		}
		else
			System.out.println(student + " already exist.");
	}
}

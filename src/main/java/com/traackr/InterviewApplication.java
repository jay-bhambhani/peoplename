package com.traackr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class InterviewApplication {


	public static void main(String[] args) throws IOException{
		SpringApplication.run(InterviewApplication.class, args);
		InterviewAnswers interviewAnswers = new InterviewAnswers();
		Set<String> uniqueFirstNames= interviewAnswers.getUniqueNames(interviewAnswers.getFirstNames());
		System.out.println("unique first names: " + uniqueFirstNames);
		Set<String> uniqueLastNames= interviewAnswers.getUniqueNames(interviewAnswers.getLastNames());
		System.out.println("unique last names: " + uniqueLastNames);
		Set<String> uniqueFullNames= interviewAnswers.getUniqueNames(interviewAnswers.getFullNames());
		System.out.println("unique full names: " + uniqueFullNames);
		LinkedHashMap<String, Integer> popularFirstNames= interviewAnswers.getMostPopular(interviewAnswers.getFirstNames());
		System.out.println("most popular first names: " + popularFirstNames);
		LinkedHashMap<String, Integer> popularLastNames= interviewAnswers.getMostPopular(interviewAnswers.getLastNames());
		System.out.println("most popular last names: " + popularLastNames);
		List<String> modifiedNames = interviewAnswers.getModifiedNames(25);
		System.out.println("modified names: " + modifiedNames);

	}

}

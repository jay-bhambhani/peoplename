package com.traackr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InterviewApplication.class)
public class InterviewApplicationTests {

	@Before
	public void setUpBeforeClass() throws IOException {
		InterviewAnswers interviewAnswers = new InterviewAnswers();
	}

	@Test
	public void contextLoads() {
	}



	@Test
	public void uniqueCounts() throws IOException {
		InterviewAnswers interviewAnswers = new InterviewAnswers();
		Set<String> firstNames= interviewAnswers.getUniqueNames("firstname");
		Assert.assertNotNull(firstNames);
		Set<String> lastNames= interviewAnswers.getUniqueNames("lastname");
		Assert.assertNotNull(lastNames);
		Set<String> fullNames= interviewAnswers.getUniqueNames("fullname");
		Assert.assertNotNull(fullNames);


	}

	@Test
	public void popularCounts() throws IOException {
		InterviewAnswers interviewAnswers = new InterviewAnswers();
		LinkedHashMap<String, Integer> popularFirstNames= interviewAnswers.getMostPopular("firstname");
		String mostPopularFirst = popularFirstNames.entrySet()
				.stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
				.get()
				.getKey();

		Assert.assertTrue(mostPopularFirst.equals("Tara"));
		LinkedHashMap<String, Integer> popularLastNames= interviewAnswers.getMostPopular("lastname");
		String mostPopularLast = popularLastNames.entrySet()
				.stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
				.get()
				.getKey();
		Assert.assertTrue(mostPopularLast.equals("Barton"));

	}

	@Test
	public void modifiedNames() throws IOException {
		InterviewAnswers interviewAnswers = new InterviewAnswers();
		Set<String> fullNames= interviewAnswers.getUniqueNames("fullname");
		List<String> modifiedNames = interviewAnswers.getModifiedNames(25);
		for (String name: modifiedNames) {
			Assert.assertTrue(!fullNames.contains(name));
		}
	}
}

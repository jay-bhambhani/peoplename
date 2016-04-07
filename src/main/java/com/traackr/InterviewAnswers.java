package com.traackr;

import javafx.scene.AmbientLight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jbhambhani on 4/6/16.
 */
public class InterviewAnswers {

    private Map<String, AtomicInteger> firstNames;
    private Map<String, AtomicInteger> lastNames;
    private Map<String, AtomicInteger> fullNames;


    public InterviewAnswers() throws IOException {
    }


    public HashMap<String, Integer> getNames(String nameType) throws IOException {
        File file = new File("/Users/jbhambhani/Downloads/People_Name_Coding_Test/coding-test-data.txt");
        Scanner input = new Scanner(file);
        HashMap<String, Integer> names = new HashMap<String, Integer>();
        int lineNum = 0;

        while (input.hasNext()) {
            String nextLine = input.nextLine();
            if (lineNum % 2 == 0) {
                List<String> splitLine = Arrays.asList(nextLine.split(" -- "));
                List<String> registeredName = Arrays.asList(splitLine.get(0).split(", "));
                String usedName;
                if (nameType.equals("firstname")) {
                    usedName = registeredName.get(1);
                } else if (nameType.equals("lastname")) {
                    usedName = registeredName.get(0);
                } else if (nameType.equals("fullname")) {
                    usedName = registeredName.get(1) + " " + registeredName.get(0);
                } else usedName = "error: no name type suggested";


                if (names.containsKey(usedName)) {
                    names.put(usedName, names.get(usedName) + 1);
                } else {
                    names.put(usedName, new Integer(1));
                }


            }
            lineNum++;
        }
        input.close();
        ;
        return names;
    }

    // lovin those lambdas!
    public Set<String> getUniqueNames(String nameType) throws IOException {
        return getNames(nameType).entrySet()
                .stream()
                .filter(entry -> entry.getValue().intValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    // datasets calculated from raw multiple times to address each question individually
    public LinkedHashMap<String, Integer> getMostPopular(String nameType) throws IOException {
        LinkedHashMap<String, Integer> result = getNames(nameType)
                .entrySet()
                .stream()
                .sorted(byValue.reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (x, y) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap<String, Integer>::new));
        return result;
    }

    private Comparator<Map.Entry<String, Integer>> byValue = (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue());


    // found it hard to reuse earlier code for this one
    public List<String> getModifiedNames(int nameCount) throws IOException {
        File file = new File("/Users/jbhambhani/Downloads/People_Name_Coding_Test/coding-test-data.txt");
        Scanner input = new Scanner(file);
        int lineNum = 0;
        List<String> modifiedNames = new ArrayList<String>();
        List<String> seenFirstNames = new ArrayList<String>();
        List<String> seenLastNames = new ArrayList<String>();

        while (lineNum < nameCount) {
            String nextLine = input.nextLine();
            if (lineNum % 2 == 0) {
                List<String> splitLine = Arrays.asList(nextLine.split(" -- "));
                List<String> registeredName = Arrays.asList(splitLine.get(0).split(", "));
                String firstName = registeredName.get(1);
                String lastName = registeredName.get(0);
                if (seenFirstNames.contains(firstName)) {
                    continue;
                }
                else {
                    seenFirstNames.add(firstName);
                }
                if (seenLastNames.contains(lastName)) {
                    continue;
                }
                else {
                    seenLastNames.add(lastName);
                }
            }
            lineNum ++;
        }

        int offset = seenFirstNames.size() - 1 % seenFirstNames.size();
        for (int i = 0; i < seenFirstNames.size(); ++i) {
            int j = (i + offset) % seenFirstNames.size();
            modifiedNames.add(seenFirstNames.get(j) + " " + seenLastNames.get(i));
        }
        return modifiedNames;
    }

}
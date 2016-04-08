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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jbhambhani on 4/6/16.
 */
public class InterviewAnswers {

    private Map<String, Integer> firstNames;
    private Map<String, Integer> lastNames;
    private Map<String, Integer> fullNames;
    private List<String> seenFirstNames;
    private List<String> seenLastNames;


    public InterviewAnswers() throws IOException {
        this.firstNames = new HashMap<String, Integer>();
        this.lastNames = new HashMap<String, Integer>();
        this.fullNames = new HashMap<String, Integer>();
        this.seenFirstNames = new ArrayList<String>();
        this.seenLastNames = new ArrayList<String>();

        parseData();
    }


    public void parseData() throws IOException {
        File file = new File("/Users/jbhambhani/Downloads/People_Name_Coding_Test/coding-test-data.txt");
        Scanner input = new Scanner(file);
        int lineNum = 0;

        while (input.hasNext()) {
            String nextLine = input.nextLine();
            if (lineNum % 2 == 0) {

                List<String> splitLine = getTokens("[a-zA-Z, ']+", nextLine);
                List<String> registeredName = getTokens("[a-zA-Z']+", splitLine.get(0));
                String firstName = registeredName.get(1);
                addToSeen(firstName, this.seenFirstNames);
                addToCounts(firstName, this.firstNames);
                String lastName = registeredName.get(0);
                addToSeen(lastName, this.seenLastNames);
                addToCounts(lastName, this.lastNames);
                String fullName = firstName + " " + lastName;
                addToCounts(fullName, this.fullNames);
            }
            lineNum++;
        }
        input.close();
    }




    public Set<String> getUniqueNames(Map<String, Integer> nameCounts) throws IOException {
        return nameCounts.entrySet()
                .stream()
                .filter(entry -> entry.getValue().intValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    public LinkedHashMap<String, Integer> getMostPopular(Map<String, Integer> nameCounts) throws IOException {
        LinkedHashMap<String, Integer> result = nameCounts
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



    public List<String> getModifiedNames(int nameCount) throws IOException {
        List<String> modifiedNames = new ArrayList<String>();
        List<String> croppedFirstNames = this.seenFirstNames.subList(0, nameCount);
        List<String> croppedLastNames = this.seenLastNames.subList(0, nameCount);

        int offset = croppedFirstNames.size() - 1 % croppedFirstNames.size();
        for (int i = 0; i < croppedFirstNames.size(); ++i) {
            int j = (i + offset) % croppedFirstNames.size();
            modifiedNames.add(croppedFirstNames.get(j) + " " + croppedLastNames.get(i));
        }
        return modifiedNames;
    }

    public Map<String, Integer> getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(Map<String, Integer> firstNames) {
        this.firstNames = firstNames;
    }

    public Map<String, Integer> getLastNames() {
        return lastNames;
    }

    public void setLastNames(Map<String, Integer> lastNames) {
        this.lastNames = lastNames;
    }

    public Map<String, Integer> getFullNames() {
        return fullNames;
    }

    public void setFullNames(Map<String, Integer> fullNames) {
        this.fullNames = fullNames;
    }

    public List<String> getSeenFirstNames() {
        return seenFirstNames;
    }

    public void setSeenFirstNames(List<String> seenFirstNames) {
        this.seenFirstNames = seenFirstNames;
    }

    public List<String> getSeenLastNames() {
        return seenLastNames;
    }

    public void setSeenLastNames(List<String> seenLastNames) {
        this.seenLastNames = seenLastNames;
    }

    private Comparator<Map.Entry<String, Integer>> byValue = (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue());

    private void addToSeen (String name, List<String> seen) {
        if (seen.contains(name)) {

        } else {
            seen.add(name);
        }
    }

    private void addToCounts (String name, Map<String, Integer> names) {
        if (names.containsKey(name)) {
            names.put(name, names.get(name) + 1);
        } else {
            names.put(name, new Integer(1));
        }
    }

    protected List<String> getTokens(String pattern, String line)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(line);

        while (m.find()) {
            tokens.add(m.group());
        }


        return tokens;
    }


}
package com.example.task03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {

        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"), Charset.forName("windows-1251"));
        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }

    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
        List<String> words = reader.lines()
                .map(String::toLowerCase)
                .filter(word -> word.length() >= 3 && word.matches("[а-яё]+"))
                .distinct()
                .collect(Collectors.toList());

        Map<String, Set<String>> anagramGroups = new HashMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars);
            anagramGroups.computeIfAbsent(sortedWord, k -> new TreeSet<>()).add(word);
        }

        return anagramGroups.values().stream()
                .filter(group -> group.size() > 1)
                .sorted(Comparator.comparing(group -> group.iterator().next()))
                .collect(Collectors.toList());
        }
}

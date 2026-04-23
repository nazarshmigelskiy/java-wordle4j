package ru.yandex.practicum;

import java.util.*;

public class HelpMeStepBro {
    private Set<Character> excludedLetter = new HashSet<>();
    private Map<Integer, Character> correctLetterPosition = new HashMap<>();
    private Map<Integer, Set<Character>> wrongPosition = new HashMap<>();
    private Set<Character> required = new HashSet<>();
    private Random random = new Random();

    public void update(String userInput, String pattern) {
        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            char p = pattern.charAt(i);
            if (p == '+') {
                correctLetterPosition.put(i, c);
                required.add(c);
            } else if (p == '^') {
                required.add(c);
                wrongPosition.computeIfAbsent(i, k -> new HashSet<>()).add(c);
            }
        }
        for (int i = 0; i < userInput.length(); i++) {
            char g = userInput.charAt(i);
            char p = pattern.charAt(i);

            if (p == '-' && !required.contains(g)) {
                excludedLetter.add(g);
            }
        }
    }


    private boolean matches(String word) {
        for (Map.Entry<Integer, Character> e : correctLetterPosition.entrySet()) {
            if (word.charAt(e.getKey()) != e.getValue()) {
                return false;
            }
        }
        for (Map.Entry<Integer, Set<Character>> e : wrongPosition.entrySet()) {
            if (e.getValue().contains(word.charAt(e.getKey()))) {
                return false;
            }
        }
        for (char c : required) {
            if (word.indexOf(c) == -1) {
                return false;
            }
        }
        for (char c : excludedLetter) {
            if (word.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }


    public List<String> getAllAdvises(List<String> dictionary) {
        List<String> result = new ArrayList<>();
        for (String word : dictionary) {
            if (matches(word)) {
                result.add(word);
            }
        }
        return result;
    }


    public String imStuck(List<String> dictionary) {
        List<String> advises = getAllAdvises(dictionary);
        if (advises.isEmpty()) {
            return dictionary.get(random.nextInt(dictionary.size()));
        }
        return advises.get(random.nextInt(advises.size()));
    }
}





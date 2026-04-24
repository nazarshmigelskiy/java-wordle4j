package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvisorTest {

    @Test
    void shouldFilterByRightPosition() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("айцук", "+----"); // первая буква 'м'
        List<String> dictionary = List.of("ааааа", "абббб", "ббббб");
        List<String> result = advisor.getAllAdvises(dictionary);
        assertTrue(result.contains("ааааа"));
        assertTrue(result.contains("абббб"));
        assertFalse(result.contains("ббббб"));
    }

    @Test
    void shouldFilterByWrongPosition() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("абвгд", "^----");
        List<String> dictionary = List.of("абвгд", "йаааа", "яаааа");
        List<String> result = advisor.getAllAdvises(dictionary);
        assertFalse(result.contains("абвгд"));
    }


    @Test
    void shouldBeRequireLetterPresence() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("аъъъъ", "^----");
        List<String> dictionary = List.of("баааа", "бббба", "абббб", "ббббб");
        List<String> result = advisor.getAllAdvises(dictionary);
        assertTrue(result.contains("баааа"));
        assertTrue(result.contains("бббба"));
        assertFalse(result.contains("абббб"));
        assertFalse(result.contains("ббббб"));
    }

    @Test
    void shouldExcludeAllLetters() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("абвгд", "-----"); // все буквы исключаются
        List<String> dictionary = List.of("абвгд", "йцука", "ячсми");
        List<String> result = advisor.getAllAdvises(dictionary);
        assertTrue(result.contains("ячсми"));
        assertFalse(result.contains("абвгд"));
        assertFalse(result.contains("йцука"));
    }

    @Test
    void ShouldReturnWordFromDictionaryIfNoMatches() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("ааааа", "-----");
        List<String> dictionary = List.of("ааааа", "абаба", "ббббб");
        String result = advisor.imStuck(dictionary);
        assertNotNull(result);
        assertTrue(dictionary.contains(result));
    }

    @Test
    void ShouldReturnOnlyValidWordsIfMatches() {
        HelpMeStepBro advisor = new HelpMeStepBro();
        advisor.update("абббб", "+----");
        List<String> dictionary = List.of("абббб", "айййй", "ббббб");
        String result = advisor.imStuck(dictionary);
        assertTrue(result.equals("абббб") || result.equals("айййй"));
    }
}


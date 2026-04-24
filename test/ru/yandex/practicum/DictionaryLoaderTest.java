package ru.yandex.practicum;
import Exceptions.DictionaryIsEmptyException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryLoaderTest {
    Writer log = new StringWriter();
    WordleDictionaryLoader loader = new WordleDictionaryLoader(log);

    @Test
    void shouldLoadDictionary() throws IOException, DictionaryIsEmptyException {
        List<String> words = loader.loadDictionary();
        assertFalse(words.isEmpty());
    }

    @Test
    void shouldHaveOnlyFiveLetterWords() throws IOException, DictionaryIsEmptyException {
        WordleDictionary dictionary = loader.createWordleDictionary();
        for (String word : dictionary.getWords()) {
            assertEquals(5, word.length());
        }
    }

    @Test
    void shouldReplaceYoWitchE() throws IOException, DictionaryIsEmptyException {
        WordleDictionary dictionary = loader.createWordleDictionary();
        boolean isYo = false;
        for (String word : dictionary.getWords()) {
            if (word.contains("ё")) {
                isYo = true;
            }
        }
        assertFalse(isYo);
    }


}

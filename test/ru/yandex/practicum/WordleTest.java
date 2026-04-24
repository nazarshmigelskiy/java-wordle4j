package ru.yandex.practicum;

import Exceptions.NotRightWordLengthException;
import Exceptions.NotRussianLettersException;
import Exceptions.WordNotFoundInDictionaryException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    Writer log = new StringWriter();
    List<String> words = List.of("ааааа", "абвгд");
    WordleDictionary dictionary = new WordleDictionary(words, log);

    @Test
    void shouldReturnAllPluses() throws WordNotFoundInDictionaryException, IOException, NotRussianLettersException, NotRightWordLengthException {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааа");
        String result = game.compareWords("ааааа");
        assertEquals("+++++ Оставшееся количество попыток: 5", result);
    }

    @Test
    void shouldReturnMixedPattern() throws WordNotFoundInDictionaryException, IOException, NotRussianLettersException, NotRightWordLengthException {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааб");
        String result = game.compareWords("абвгд");
        assertEquals("+^--- Оставшееся количество попыток: 5", result);
    }

    @Test
    void shouldDecreaseStepsAfterGuess() throws WordNotFoundInDictionaryException, IOException, NotRussianLettersException, NotRightWordLengthException {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааб");
        String result = game.compareWords("ааааа");
        assertEquals(5, game.getSteps());
    }

    @Test
    void shouldThrowExceptionIfWrongLength() {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааа");
        assertThrows(NotRightWordLengthException.class, () -> {
            game.compareWords("ааа");
        });
    }

    @Test
    void shouldThrowExceptionIfInvalidChars() {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааа");
        assertThrows(NotRussianLettersException.class, () -> {
            game.compareWords("qwert");
        });
    }

    @Test
    void shouldThrowExceptionIfWordNotInDictionary() {
        WordleGame game = new WordleGame(log, dictionary, 6, "ааааа");
        assertThrows(WordNotFoundInDictionaryException.class, () -> {
            game.compareWords("ыафпц");
        });
    }


}

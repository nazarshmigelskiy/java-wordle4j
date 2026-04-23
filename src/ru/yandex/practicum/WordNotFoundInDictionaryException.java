package ru.yandex.practicum;

public class WordNotFoundInDictionaryException extends Exception {

    WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}

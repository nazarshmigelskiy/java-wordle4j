package ru.yandex.practicum;

public class NotRussianLettersException extends Exception {
    NotRussianLettersException(String message) {
        super(message);
    }

}

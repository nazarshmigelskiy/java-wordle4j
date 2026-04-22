package ru.yandex.practicum;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private final Writer log;
    private boolean isWinner = false;
    private HelpMeStepBro advisor = new HelpMeStepBro();

    public WordleGame(Writer log, WordleDictionary dictionary, int steps, String answer) {
        this.log = log;
        this.dictionary = dictionary;
        this.steps = steps;
        this.answer = answer;
    }


    public String compareWords(String userInput) throws IOException, NotRightWordLengthException,
            NotRussianLettersException, WordNotFoundInDictionaryException {
        if (userInput.length() != 5) {
            throw new NotRightWordLengthException("Слово должно состоять из 5 букв");
        }
        if (hasInvalidChars(userInput)) {
            throw new NotRussianLettersException("Слово должно состоять только из русских букв");
        }
        String pattern = dictionary.getLetterEquality(formateWord(userInput), answer);
        steps--;
        advisor.update(userInput, pattern);
        return pattern;
    }


    public boolean hasInvalidChars(String userInput) {
        for (char c : userInput.toLowerCase().toCharArray()) {
            if (!(c >= 'а' && c <= 'я') && c != 'ё') {
                return true;
            }
        }
        return false;
    }

    public String formateWord(String userInput) {
        String formattedWord = userInput.toLowerCase();
        if (formattedWord.contains("ё")) {
            StringBuilder sb = new StringBuilder(formattedWord);
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == 'ё') {
                    sb.setCharAt(i, 'е');
                }
            }
            formattedWord = sb.toString();
        }
        return formattedWord;
    }

    public String getAdvise() throws IOException {
        log.write("Вызвана подсказка\n");
        return advisor.imStuck(dictionary.getWords());

    }


    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

}



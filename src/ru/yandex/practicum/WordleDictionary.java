package ru.yandex.practicum;


import exceptions.WordNotFoundInDictionaryException;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;
    private final Writer log;

    public WordleDictionary(List<String> words, Writer log) {
        this.words = words;
        this.log = log;
    }

    public List<String> getWords() {
        return words;
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public String getLetterEquality(String userWord, String answer)
            throws StringIndexOutOfBoundsException, IOException, WordNotFoundInDictionaryException {
        if (userWord.equals(answer)) {
            return "+++++";
        } else if (!words.contains(userWord)) {
            throw new WordNotFoundInDictionaryException("Этого слова нет в нашем словаре");
        } else  {
            StringBuilder sb = new StringBuilder(5);
            for (int i = 0;  i < userWord.length(); i++) {
                    if (answer.charAt(i) == userWord.charAt(i)) {
                        sb.append('+');
                    } else if (answer.contains(userWord.charAt(i) + "")) {
                        sb.append('^');
                    } else {
                        sb.append('-');
                }
            }
            log.write(String.format("Пользователем введено слово: %s%n", userWord));
            log.write(String.format("Вывод в консоль: %s%n", sb));

        return sb.toString();
        }
    }


    public void printDictionary() {
        for (String word : words) {
            System.out.println(word);
        }
    }
}

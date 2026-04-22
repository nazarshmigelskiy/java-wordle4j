package ru.yandex.practicum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final Writer log;


    public WordleDictionaryLoader(Writer log) {
        this.log = log;
    }

    public List<String> loadDictionary() throws IOException, DictionaryIsEmptyException {
        List<String> words = new ArrayList<>();
        try (Reader fileReader = new FileReader(("words_ru.txt"), UTF_8)) {
            BufferedReader br = new BufferedReader(fileReader);
            while (br.ready()) {
                String line = br.readLine().toLowerCase();
                words.add(line);
            }
            if (words.isEmpty()) {
                throw new DictionaryIsEmptyException("Словарь пуст.");
            }
        }
        log.write("Словарь успешно загружен\n");
        return words;
    }



    public WordleDictionary createWordleDictionary() throws IOException, DictionaryIsEmptyException {

        List<String> dictionary = loadDictionary();
        List<String> wordlieDictionary = new ArrayList<>();
            for (String s : dictionary) {
                if (s.length() == 5) {
                    if (s.contains("ё")) {
                        StringBuilder sb = new StringBuilder(s);
                        for (int i = 0; i < sb.length(); i++) {
                            if (sb.charAt(i) == 'ё') {
                                sb.setCharAt(i, 'е');
                            }
                        }
                        s = sb.toString();
                    }
                    wordlieDictionary.add(s);
                }
            }
            log.write("Словарь отсортирован и приведен к единому виду\n");
            return new WordleDictionary(wordlieDictionary, log);

    }
}

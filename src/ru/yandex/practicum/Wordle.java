package ru.yandex.practicum;

import Exceptions.NotRightWordLengthException;
import Exceptions.NotRussianLettersException;
import Exceptions.WordNotFoundInDictionaryException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) throws IOException {
        final Writer log = new FileWriter("log.txt", true);
        try (log) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.createWordleDictionary();
            WordleGame game = new WordleGame(log, dictionary, 6, dictionary.getRandomWord());
            log.write(String.format("Загаданное слово: %s%n", game.getAnswer()));
            Scanner scanner = new Scanner(System.in);
            System.out.println(game.getRules());
            System.out.println("Введите слово:");
            while (game.getSteps() != 0 && !game.isWinner()) {
                try {
                    String userInput = scanner.nextLine();
                    if (game.formateWord(userInput).equals("стоп")) {
                        System.out.println("Выход из игры.");
                        break;
                    }
                    if (userInput.isBlank()) {
                        System.out.printf("%nПодсказка: %s%n", game.getAdvise());
                        continue;
                    }
                    String comparedWord = game.compareWords(userInput);
                    if (comparedWord.equals("+++++")) {
                        System.out.println("+++++");
                        game.setWinner(true);
                        System.out.printf("Верно! Загаданное слово: %s%n", game.getAnswer());
                        log.write(String.format("Игра успешно завершена! Загаданное слово: %s, осталось попыток: %d%n",
                                game.getAnswer(), game.getSteps()));
                    } else {
                        System.out.println(comparedWord);
                        if (game.getSteps() == 0 && !game.isWinner()) {
                            System.out.printf("К сожалению, попытки закончились. Загаданное слово: %s%n", game.getAnswer());
                        }
                    }

                } catch (NotRightWordLengthException | NotRussianLettersException |
                         WordNotFoundInDictionaryException e) {
                    System.out.println(e.getMessage());
                    log.write(e.getMessage() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

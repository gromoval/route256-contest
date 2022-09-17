package Ozon.Contest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/*
C. Декодирование строки (10 баллов)
ограничение по времени на тест2 секунды
ограничение по памяти на тест256 мегабайт
вводстандартный ввод
выводстандартный вывод
Строка, состоящая из первых четырёх строчных букв латинского алфавита (то есть из 'a', 'b', 'c' и 'd'), была закодирована следующим образом:

каждая буква 'a' была закодирована как 00;
каждая буква 'b' была закодирована как 100;
каждая буква 'c' была закодирована как 101;
каждая буква 'd' была закодирована как 11.
Например, в результате кодирования строки «badcac» получается «100001110100101».

Для заданной последовательности цифр 0 и 1 осуществите декодирование в исходную строку.

Входные данные
В первой строке входных данных записано целое число t (1≤t≤1000) — количество наборов входных данных.

Наборы входных данных в тесте являются независимыми. Друг на друга они никак не влияют.

Каждые набор входных данных задаётся одной строкой, состоящей из символов 0 и 1. Длина строки — от 2 до 50 символов, включительно.

Гарантируется, что заданная строка была получена в результате кодирования некоторой строки, состоящей из первых четырёх строчных букв латинского алфавита, по написанным выше правилам.

Выходные данные
Выведите t строк. Каждая строка должны содержать только буквы 'a', 'b', 'c' и 'd' и являться ответом для соответствующего набора входных данных.

Пример
входные данные
5
100001110100101
00
100100100100100100100
10111
101

выходные данные
badcac
a
bbbbbbb
cd
c
*/

public class TaskC {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.lines().collect(Collectors.toList());

        decode(input).forEach(System.out::println);
    }

    private static List<String> decode(List<String> input) {
        return input.stream()
                .skip(1)
                .map(s -> {
                    var begin = 0;
                    StringBuilder sb = new StringBuilder(s);
                    while (begin < sb.length()) {
                        if (sb.substring(begin, begin + 2).equals("00")) {
                            sb.replace(begin, begin + 2, "a");
                            begin += 1;
                            continue;
                        }
                        if (sb.substring(begin, begin + 2).equals("11")) {
                            sb.replace(begin, begin + 2, "d");
                            begin += 1;
                            continue;
                        }
                        if (sb.substring(begin, begin + 3).equals("100")) {
                            sb.replace(begin, begin + 3, "b");
                            begin += 1;
                            continue;
                        }
                        if (sb.substring(begin, begin + 3).equals("101")) {
                            sb.replace(begin, begin + 3, "c");
                            begin += 1;
                            continue;
                        }
                        break;
                    }
                    return sb.toString();
                })
                .collect(Collectors.toList());
    }
}

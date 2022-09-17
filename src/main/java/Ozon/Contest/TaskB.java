package Ozon.Contest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/*
B. Проверка даты (10 баллов)
ограничение по времени на тест2 секунды
ограничение по памяти на тест256 мегабайт
ввод стандартный ввод
вывод стандартный вывод
Задана дата в формате день месяц год в виде трёх целых чисел. Гарантируется, что:

день — это целое число от 1 до 31,
месяц — это целое число от 1 до 12,
год — это целое число от 1950 до 2300.
Проверьте, что заданные три числа соответствуют корректной дате (в современном григорианском календаре).

Напоминаем, что в соответствии с современным календарём год считает високосным, если для этого года верно хотя бы одно из утверждений:

делится на 4, но при этом не делится на 100;
делится на 400.
Например, годы 2012 и 2000 являются високосными, но годы 1999, 2022 и 2100 — нет.

Входные данные
В первой строке записано целое число t (1≤t≤1000) — количество наборов входных данных в тесте.

Наборы входных данных в тесте являются независимыми. Друг на друга они никак не влияют.

Каждые набор входных данных задаётся одной строкой, в которой записаны три целых числа d, m, y (1≤d≤31,1≤m≤12,1950≤y≤2300) — день, месяц и год даты для проверки.

Выходные данные
Для каждого набора входных данных выведите YES, если соответствующая дата является корректной (т.е. существует такая дата в современном календаре). Выведите NO в противном случае.

Вы можете выводить ответ в любом регистре (например, вывод yEs, yes, Yes и YES всё ещё будет считаться корректным).

Пример
входные данные
10
10 9 2022
21 9 2022
29 2 2022
31 2 2022
29 2 2000
29 2 2100
31 11 1999
31 12 1999
29 2 2024
29 2 2023

выходные данные
YES
YES
NO
NO
YES
NO
NO
YES
YES
NO
*/

public class TaskB {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.lines().collect(Collectors.toList());

        validateDate(input).forEach(System.out::println);
    }

    private static List<String> validateDate(List<String> input) {
        return input.stream()
                .skip(1)
                .map(s -> {
                    var date = s.split("\\s+");
                    var day = Integer.parseInt(date[0]);
                    var month = Integer.parseInt(date[1]);
                    var year = Integer.parseInt(date[2]);
                    if (day > 31 || day < 1 || month > 12 || month < 1 || year > 2300 || year < 1950)
                        return "NO";
                    else if (((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) && 2 == month && day == 29)
                        return "YES";
                    else if (2 == month && day > 28) return "NO";
                    else if ((4 == month || 6 == month || 9 == month || 11 == month) && day > 30) return "NO";
                    return "YES";
                })
                .collect(Collectors.toList());
    }
}

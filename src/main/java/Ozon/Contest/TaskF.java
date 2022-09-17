package Ozon.Contest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
F. Противоположные элементы (20 баллов)
ограничение по времени на тест10 секунд
ограничение по памяти на тест256 мегабайт
вводстандартный ввод
выводстандартный вывод
Вам задан циклический двусвязный список целых чисел. Иными словами, заданный список замкнут в «кольцо», а для каждого его элемента вам известны его соседи (в произвольном порядке).

Список задан набором троек вида «eiaibi». Каждая такая тройка означает, что соседями элемента ei являются элементы ai и bi. У вас нет информации какой из этих двух элементов следующий, а какой предыдущий. Иными словами, соседи ai и bi заданы в соответствующей тройке в произвольном порядке.

Известно, что всего в списке чётное количество элементов.

Для каждого элемента выведите тот, который ему противоположен (то есть расположен строго напротив, если изобразить список в виде правильного n-угольника).

Неполные решения этой задачи (например, недостаточно эффективные) могут быть оценены частичным баллом.

Входные данные
В первой строке входных данных записано целое число t (1≤t≤104) — количество наборов входных данных в тесте. Далее следуют описания наборов входных данных.

Наборы входных данных в тесте являются независимыми. Друг на друга они никак не влияют.

В первой строке набора записано чётное целое число n (4≤n≤105) — длина списка.

Следующие n строк содержат тройки ei,ai,bi (1≤ei,ai,bi≤109), которые обозначают, что соседями элемента ei являются элементы ai и bi. Гарантируется, что все n элементов списка — различные числа.

Сумма значений n по всем наборам входных данных теста не превосходит 105.

Выходные данные
Для каждого набора выходных данных выведите n/2 строк, каждая из которых должна содержать два целых числа xi,yi, которые означают, что элемент xi расположен строго напротив элемента yi в циклическом списке. Числа в парах и сами пары можно выводить в любом порядке.

Для улучшения читаемости ответа между ответами для наборов выходных данных можно выводить пустую строку.

Пример
входные данные
2
4
4 2 1
2 4 3
3 2 1
1 3 4
6
20 30 10
40 30 50
30 20 40
60 10 50
10 60 20
50 40 60

выходные данные
4 3
1 2

30 60
20 50
40 10
*/

public class TaskF {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.lines().collect(Collectors.toList());

        findOpposites(input);
    }

    private static void findOpposites(List<String> input) {
        var graphCount = Integer.parseInt(input.get(0));
        var filteredInput = input.stream().skip(1).collect(Collectors.toList());
        List<String[][]> mappedInput = new ArrayList<>();

        int from = 0;
        for (int i = 0; i < graphCount; i++) {
            var to = Integer.parseInt(Arrays.stream(filteredInput.get(from).split("\\s+")).findFirst().get());
            mappedInput.add(IntStream.rangeClosed(from + 1, from + to)
                    .parallel()
                    .mapToObj(n -> filteredInput.get(n).split("\\s+"))
                    .toArray(String[][]::new));
            from += to + 1;
        }

        for (String[][] graph : mappedInput) {
            Map<String, Ver> poly = new HashMap<>();
            String primaryKey = "";

            for (String[] strings : graph) {
                if (null == poly.get(strings[0])) {
                    poly.put(strings[0], new Ver(strings[1], strings[2]));
                }
                primaryKey = strings[0];
            }
            List<String> list = new ArrayList<>();
            String value = poly.get(primaryKey).getRight();

            for (int i = graph.length; i > 0; i -= 2) {
                list.add(primaryKey);
                list.add(value);

                if (poly.get(value).getRight().equals(primaryKey)) {
                    primaryKey = poly.get(value).getLeft();
                } else {
                    primaryKey = poly.get(value).getRight();
                }

                if (poly.get(primaryKey).getRight().equals(value)) {
                    value = poly.get(primaryKey).getLeft();
                } else {
                    value = poly.get(primaryKey).getRight();
                }
            }

            int offset = list.size() / 2;
            for (int i = 0; i < offset; i++) {
                System.out.printf("%s %s%n", list.get(i), list.get(i + offset));
            }
            System.out.printf("%n");
        }
    }
}

class Ver {
    private String right;
    private String left;

    public Ver(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getRight() {
        return right;
    }

    public String getLeft() {
        return left;
    }
}

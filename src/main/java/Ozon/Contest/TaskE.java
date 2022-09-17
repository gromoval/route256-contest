package Ozon.Contest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
E. Путь фишки (20 баллов)
ограничение по времени на тест4 секунды
ограничение по памяти на тест256 мегабайт
вводстандартный ввод
выводстандартный вывод
В некоторую клетку прямоугольного поля была поставлена фишка. После этого был совершен один или более ход. Каждый ход фишку перемещали на соседнюю по стороне клетку вправо/влево/вверх или вниз.

Известно, что фишка не посещала одну клетку дважды. Гарантируется, что любые две соседние по стороне посещенные клетки поля — это две последовательные клетки в пути фишки. Иными словами, путь фишки не содержит самопересечений и самокасаний.

Вам дано поле, на котором отмечены посещённые клетки. Посещённые клетки обозначены символами '*' (звёздочка), а непосещённые клетки — символами '.' (точка).

Выведите любой возможный путь фишки в виде строки из букв 'R', 'L', 'U', 'D' (означающих перемещение вправо/влево/вверх/вниз соответственно).

Например, заданное поле может иметь вид:

.*....
.*.***
.***.*
.....*
......
В этом случае ответ равен любой из двух строк: DDRRURRDD или UULLDLLUU.

Напишите программу, которая по заданному полю находит любой возможный путь фишки, который проходит исключительно по отмеченным клеткам и посещает все отмеченные клетки поля ровно по одному разу.

Неполные решения этой задачи (например, недостаточно эффективные) могут быть оценены частичным баллом.

Входные данные
В первой строке входных данных записано целое число t (1≤t≤100) — количество наборов входных данных в тесте.

Наборы входных данных в тесте являются независимыми. Друг на друга они никак не влияют.

Вторая строка содержит два целых числа n и m (1≤n,m≤100) — размеры поля. Гарантируется, что поле содержит хотя бы 2 клетки (то есть случай n=m=1 недопустим).

Следующие n строк описывают поле. Каждая из них содержит по m символов. Каждый символ — это либо точка ('.'), либо звёздочка ('*').

Гарантируется, что все звёздочки образуют один путь фишки. Более того, любые две соседние по стороне звёздочки являются соседними в пути фишки. Иными словами, путь фишки не может пересекать себя и не может сам себя касаться.

Поле содержит хотя бы две звёздочки.

Выходные данные
Для каждого набора входных данных выведите любой из возможных путей фишки, который посетит по одному разу звёздочки и только их.

Пример
входные данные
2
5 6
.*....
.*.***
.***.*
.....*
......
2 7
.***...
**.**..

выходные данные
DDRRURRDD
LULLDL
*/

public class TaskE {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.lines().collect(Collectors.toList());

        findPath(input).forEach(System.out::println);
    }

    private static List<String> findPath(List<String> input) {
        var mapCount = Integer.parseInt(input.get(0));
        var filteredInput = input.stream().skip(1).collect(Collectors.toList());
        List<String[][]> mappedInput = new ArrayList<>();

        int from = 0;
        for (int i = 0; i < mapCount; i++) {
            var to = Integer.parseInt(Arrays.stream(filteredInput.get(from).split("\\s+")).findFirst().get());
            mappedInput.add(IntStream.rangeClosed(from + 1, from + to)
                    .parallel()
                    .mapToObj(n -> filteredInput.get(n).split(""))
                    .toArray(String[][]::new));
            from += to + 1;
        }

        List<String> result = new ArrayList<>();
        for (String[][] map : mappedInput) {
            int x = findStartPoint(map, map.length, map[0].length)[0];
            int y = findStartPoint(map, map.length, map[0].length)[1];
            boolean flag = true;
            int prevType = -1;
            StringBuilder res = new StringBuilder();

            while (flag) {
                if (prevType != 2 && checkRight(map, x, y, map[0].length)) {
                    prevType = 0;
                    y += 1;
                    res.append("R");
                } else if (prevType != 0 && checkLeft(map, x, y)) {
                    prevType = 2;
                    y -= 1;
                    res.append("L");
                } else if (prevType != 3 && checkUp(map, x, y)) {
                    prevType = 1;
                    x -= 1;
                    res.append("U");
                } else if (prevType != 1 && checkDown(map, x, y, map.length)) {
                    prevType = 3;
                    x += 1;
                    res.append("D");
                } else flag = false;
            }
            result.add(res.toString());
        }
        return result;
    }

    private static int[] findStartPoint(String[][] map, int rows, int columns) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y].equals("*")) {
                    if (checkStartPoint(map, x, y, rows, columns)) {
                        return new int[]{x, y};
                    }
                }
            }
        }
        return new int[]{0, 0};
    }

    private static boolean checkStartPoint(String[][] map, int x, int y, int rows, int columns) {
        int res = 0;
        if (checkLeft(map, x, y)) res += 1;
        if (checkRight(map, x, y, columns)) res += 1;
        if (checkUp(map, x, y)) res += 1;
        if (checkDown(map, x, y, rows)) res += 1;
        return 1 == res;
    }

    private static boolean checkDown(String[][] map, int x, int y, int rows) {
        if (rows - 1 == x) return false;
        return map[x + 1][y].equals("*");
    }

    private static boolean checkUp(String[][] map, int x, int y) {
        if (0 == x) return false;
        return map[x - 1][y].equals("*");
    }

    private static boolean checkLeft(String[][] map, int x, int y) {
        if (0 == y) return false;
        return map[x][y - 1].equals("*");
    }

    private static boolean checkRight(String[][] map, int x, int y, int columns) {
        if (columns - 1 == y) return false;
        return map[x][y + 1].equals("*");
    }
}

package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  private static List<Integer> findAdjacentNumbers(List<String> rows, int[] symbolPosition) {
    List<Integer> numbers = new ArrayList<>();
    int x = symbolPosition[0];
    int y = symbolPosition[1];

    Pattern p = Pattern.compile("(\\d+)");
    Matcher m = p.matcher("");
    
    int listSize = rows.size();
    int beginRow = y == 0 ? 0 : y-1;
    int endRow = y == listSize-1 ? listSize : y+2;

    for (int i = beginRow; i < endRow; i++) {
      m.reset(rows.get(i));

      while (m.find()) {
        int s = m.start();
        int e = m.end();
        if (s <= x+1 && e >= x) {
          numbers.add(Integer.parseInt(m.group()));
        }
      }
    }

    return numbers;
  }

  private static List<Integer> result (List<String> rows) {
    int part1 = 0;
    int part2 = 0;

    Pattern symbolPattern = Pattern.compile("[*#+$&%=\\-@/]");
    Matcher symbolMatcher = symbolPattern.matcher("");

    int size = rows.size();

    for (int i = 0; i < size; i++) {
      symbolMatcher.reset(rows.get(i));

      while (symbolMatcher.find()) {
        boolean isGear = symbolMatcher.group().equals("*");

        List<Integer> adjacentNumbers = findAdjacentNumbers(
          rows, new int[]{symbolMatcher.start(), i}
        );

        int gearRatio = 1;

        for (Integer n : adjacentNumbers) {
          part1 += n;
          if (isGear)
            gearRatio *= n;
        }

        if (isGear && adjacentNumbers.size() == 2)
          part2 += gearRatio;
      }
    }

    return Arrays.asList(new Integer[]{part1, part2});
  }


  public static void main(String[] args) {

    String inputPath = Solution.class.getPackageName() + "/input.txt";
    File inputFile = new File(inputPath);

    List<String> rows = new ArrayList<>();

    try {
      Scanner scanner = new Scanner(inputFile);

      while (scanner.hasNextLine()) {
        rows.add(scanner.nextLine());
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

    List<Integer> result = result(rows);
    System.out.println("Part 1: " + result.get(0));
    System.out.println("Part 2: " + result.get(1));
  }

}

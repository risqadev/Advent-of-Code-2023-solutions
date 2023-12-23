package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  private static List<Integer> results (Map<Integer, Card> cardsMap) {
    int part1 = 0;
    int part2 = 0;
    
    int size = cardsMap.size();

    for (int id = 1; id <= size; id++) {
      int pts = cardsMap.get(id).getPoints();
      int qtd = cardsMap.get(id).getQtd();
      
      for (int i = id+1; i <= id+pts && i <= size; i++) {
        cardsMap.get(i).add(qtd);
      }
      
      part1 += (int)Math.pow(2.0, (double)(pts-1));
      part2 += qtd;
    }

    return Arrays.asList(new Integer[]{part1, part2});
  }


  public static void main(String[] args) {

    String inputPath = Solution.class.getPackageName() + "/input.txt";
    File inputFile = new File(inputPath);

    Map<Integer, Card> cardsMap = new HashMap<>();

    Pattern p = Pattern.compile("Card\\s+(?<c>\\d+):\\s+(?<w>[\\s\\d]*)\\s\\|\\s(?<h>[\\s\\d]*)$");
    Matcher m = p.matcher("");

    try {
      Scanner scanner = new Scanner(inputFile);
      
      String row;

      while (scanner.hasNextLine()) {
        row = scanner.nextLine();

        if (m.reset(row).find()) {
          int cardId = Integer.parseInt(m.group("c"));

          List<String> winningNumbers = Arrays.asList(m.group("w").split("\\s+"));
          List<String> havingNumbers = Arrays.asList(m.group("h").split("\\s+"));

          Card card = new Card(cardId, winningNumbers, havingNumbers);

          cardsMap.put(cardId, card);
        }
      }
      
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

    List<Integer> result = results(cardsMap);
    System.out.println("Part 1: " + result.get(0));
    System.out.println("Part 2: " + result.get(1));
  }

}

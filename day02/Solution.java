package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  private static boolean isGamePossible (String line, Map<String, Integer> colorLimits, Matcher colorMatcher) {
    
    colorMatcher.reset();
    
    while (colorMatcher.find()) {
      Integer n = Integer.parseInt(colorMatcher.group(1));
      String color = colorMatcher.group(2);

      if (n > colorLimits.get(color)) {
        return false;
      }
    }

    return true;
  }

  private static int gamePower (String line, Matcher colorMatcher) {
    int power = 1;
    colorMatcher.reset();
    
    Map<String, Integer> max = new HashMap<>(){{
      put("red", 0);
      put("green", 0);
      put("blue", 0);
    }};
    
    while (colorMatcher.find()) {
      Integer n = Integer.parseInt(colorMatcher.group(1));
      String color = colorMatcher.group(2);

      if (n > max.get(color)) {
        max.replace(color, n);
      }
    }

    for (var c : max.keySet()) {
      power *= max.get(c);
    }

    return power;
  }

  private static List<Integer> result (List<String> lines) {
    int part1 = 0;
    int part2 = 0;

    Map<String, Integer> colorLimits = new HashMap<>(){{
      put("red", 12);
      put("green", 13);
      put("blue", 14);
    }};

    Pattern gamePattern = Pattern.compile("^Game (\\d+):");
    Pattern colorPattern = Pattern.compile("(\\d+) (" + String.join("|", colorLimits.keySet()) + ")");
    
    for (String line : lines) {
      Matcher gameMatcher = gamePattern.matcher(line);
      Matcher colorMatcher = colorPattern.matcher(line);
      
      if (gameMatcher.find()) {
        int game = Integer.parseInt(gameMatcher.group(1).toString());
        
        if (isGamePossible(line, colorLimits, colorMatcher)) {
          part1 += game;
        }

        part2 += gamePower(line, colorMatcher);
      } else {
        System.out.println("Game not found in this line: " + line);
      }

    }

    Integer[] results = {part1, part2};

    return Arrays.asList(results);
  }

  public static void main (String[] args) {

    List<String> lines = new ArrayList<>();
    
    String inputFileName = Solution.class.getPackageName() + "/input.txt";
    File f = new File(inputFileName);

    try {
      Scanner scanner = new Scanner(f);

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        lines.add(line);
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    var result = result(lines);

    System.out.println("Part 1: " + result.get(0));
    System.out.println("Part 2: " + result.get(1));
  }
}

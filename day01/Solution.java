package day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  private static int calculateCalibration (List<String> lines) {
    int result = 0;

    Map<String, String> valuesMap = new HashMap<>(){{
      put("one", "1");
      put("two", "2");
      put("three", "3");
      put("four", "4");
      put("five", "5");
      put("six", "6");
      put("seven", "7");
      put("eight", "8");
      put("nine", "9");
    }};

    String desirableMatches = "\\d|" + String.join("|", valuesMap.keySet());

    Pattern fp = Pattern.compile("(" + desirableMatches + ")");
    Pattern lp = Pattern.compile(".*(" + desirableMatches + ").*$");

    for (String s : lines) {
      Matcher fm = fp.matcher(s);
      Matcher lm = lp.matcher(s);

      if (fm.find() && lm.find()) {
        String[] matches = {
          fm.group(1).toString(),
          lm.group(1).toString()
        };
        String concat = "";

        for (String m : matches) {
          if (valuesMap.containsKey(m)) {
            concat += valuesMap.get(m);
          } else {
            concat += m;
          }
        }

        int value = Integer.parseInt(concat);
        //System.out.println(s + " - " + value);
        result += value;
      }
    }

    return result;
  }

  public static void main (String[] args) {

    List<String> lines = new ArrayList<>();
    
    String inputFileName = "input.txt";
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
    
    int result = calculateCalibration(lines);

    System.out.println(result);
  }
}

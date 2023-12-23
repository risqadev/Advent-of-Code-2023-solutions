package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

  public static void main(String[] args) {

    boolean sampleRun = false;

    String inputPath = Solution.class.getPackageName()
                        + "/input"
                        + (sampleRun ? "_sample" : "")
                        + ".txt";
    File inputFile = new File(inputPath);
    
    try {
      Scanner blockScanner = new Scanner(inputFile).useDelimiter("\\n{2}");

      List<Long> seedsList1 = lineRead(blockScanner.next());
      
      // order: soil, fertilizer, water, light, temperature, humidity, location
      
      List<List<List<Long>>> maps = new ArrayList<>(){{
        while (blockScanner.hasNext())
          add(mapRead(blockScanner.next()));
      }};

      // part 1
      long lowestPart1 = Long.MAX_VALUE;

      for (var seed : seedsList1) {
        long location = seedLocation(seed, maps);
        if (location < lowestPart1)
          lowestPart1 = location;
      }

      System.out.println("Part 1 lowest location number: " + lowestPart1);
      
      // part 2
      long lowestPart2 = Long.MAX_VALUE;

      for (int i = 0; i < seedsList1.size(); i+=2) {
        long seed = seedsList1.get(i);
        long end = seed + seedsList1.get(i+1);

        while (seed < end) {
          // just so that the program does not appear to stop during the long execution
          if (seed % 1e6 == 0) System.out.println(seed);

          long location = seedLocation(seed, maps);
          if (location < lowestPart2)
            lowestPart2 = location;

          seed++;
        }
      }

      System.out.println("Part 2 lowest location number: " + lowestPart2);

      blockScanner.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  private static long seedLocation (long seed, List<List<List<Long>>> maps) {
    long find = seed;
          
    for (List<List<Long>> map : maps) {
      for (List<Long> line : map) {
        long start = line.get(1);
        long len = line.get(2);
        if (find >= start && find < start+len) {
          find = line.get(0) + (find - start);
          break;
        }
      }
    }

    return find;
  }

  private static List<Long> lineRead (String block) {
    Scanner scanner = new Scanner(block);

    List<Long> list = new ArrayList<>(){{
      if (scanner.hasNext("seeds:"))
        scanner.next();
      
      while (scanner.hasNextLong())
        add(scanner.nextLong());
    }};

    scanner.close();

    return list;
  }

  private static List<List<Long>> mapRead (String block) {
    Scanner scanner = new Scanner(block);

    List<List<Long>> list = new ArrayList<>(){{
      if (!scanner.hasNext("seeds:")) {
        scanner.nextLine();
        while (scanner.hasNextLine())
          add(
            lineRead(scanner.nextLine())
          );
      }
    }};

    scanner.close();

    return list;
  }

}

package day04;

import java.util.List;

public class Card {

  private final int id;
  private final int points;
  private int qtd;

  public Card(int id, List<String> w, List<String> h) {
    this.id = id;
    this.points = calculatePoints(w, h);
    this.qtd = 1;
  }

  private static int calculatePoints(List<String> w, List<String> h) {
    int p = 0;

    for (var n : h)
      if (w.contains(n))
        p++;

    return p;
  }

  public void add(int n) {
    qtd += n;
  }

  public int getId() {
    return id;
  }

  public int getPoints() {
    return points;
  }

  public int getQtd() {
    return qtd;
  }

  @Override
  public String toString() {
    return "{\n  id: " + id  +
            "\n  points: " + points +
            "\n  qtd: " + qtd + "\n}";
  }

  

}

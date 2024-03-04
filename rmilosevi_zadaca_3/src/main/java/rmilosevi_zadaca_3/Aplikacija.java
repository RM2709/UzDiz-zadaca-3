package rmilosevi_zadaca_3;

public class Aplikacija {

  public static void main(String[] args) {
    try {
      Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
      if (tvrtka.inicijaliziraj(args)) {
        tvrtka.rad();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}

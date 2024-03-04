package rmilosevi_zadaca_3;

import java.util.HashMap;

public class Caretaker {

  private static final Caretaker instanca = new Caretaker();
  private HashMap<String, Tvrtka.Memento> spremljenaStanja = new HashMap<String, Tvrtka.Memento>();
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  private Caretaker() {}

  public static Caretaker dohvatiInstancu() {
    return instanca;
  }

  public void spremiStanje(String naziv) {
    spremljenaStanja.put(naziv, tvrtka.spremiStanje());
    System.out.println("Stanje spremljeno s nazivom '" + naziv + "'");
  }

  public void ucitajStanje(String naziv) {
    if (spremljenaStanja.containsKey(naziv)) {
      tvrtka.vratiStanje(spremljenaStanja.get(naziv));
      System.out.println("Stanje s nazivom '" + naziv + "' učitano");
    } else {
      System.out.println("Stanje s nazivom '" + naziv + "' ne postoji");
    }
  }

}

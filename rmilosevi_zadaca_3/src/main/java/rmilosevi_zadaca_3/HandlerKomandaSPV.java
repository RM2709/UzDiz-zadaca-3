package rmilosevi_zadaca_3;

import java.util.regex.Pattern;

public class HandlerKomandaSPV implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Caretaker caretaker = Caretaker.dohvatiInstancu();
  Pattern uzorakSPV = Pattern.compile("^SPV '[ -ž]{1,}'$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakSPV.matcher(komanda).find()) {
      obradi(komanda);
    } else {
      if (sljedeci != null) {
        sljedeci.obradiZahtjev(komanda);
      } else {
        System.out.println("Krivi unos komande");
      }
    }
  }

  private void obradi(String komanda) {
    String[] komanda_dio = komanda.split("'");
    String naziv = komanda_dio[1].trim();
    caretaker.spremiStanje(naziv);
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

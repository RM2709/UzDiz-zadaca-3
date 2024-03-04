package rmilosevi_zadaca_3;

public class HandlerKomandaQ implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("Q") == 0) {
      System.out.println("Prekid rada programa");
    } else {
      if (sljedeci != null) {
        sljedeci.obradiZahtjev(komanda);
      } else {
        System.out.println("Krivi unos komande");
      }
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

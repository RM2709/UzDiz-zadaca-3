package rmilosevi_zadaca_3;

public class HandlerKomandaPP implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("PP") == 0) {
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
    for (Podrucje podrucje : tvrtka.getPodrucja()) {
      podrucje.naziv(false);
    }
  }


  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

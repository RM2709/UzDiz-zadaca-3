package rmilosevi_zadaca_3;

public class HandlerKomandaIPP extends Dekorator {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  public HandlerKomandaIPP(HandlerKomande operacijaIP) {
    super(operacijaIP);
  }

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("IPP") == 0) {
      obradi();
      super.obradiZahtjev("");
    } else {
      if (sljedeci != null) {
        sljedeci.obradiZahtjev(komanda);
      } else {
        System.out.println("Krivi unos komande");
      }
    }
  }

  private void obradi() {
    System.out.println("Prikupljeno je " + tvrtka.prijaviPrihod() + "€");
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

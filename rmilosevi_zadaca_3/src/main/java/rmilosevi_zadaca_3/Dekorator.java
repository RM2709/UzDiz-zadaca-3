package rmilosevi_zadaca_3;

public class Dekorator implements HandlerKomande {

  private HandlerKomande operacijaIP;
  private HandlerKomande sljedeci;
  private Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  public Dekorator(HandlerKomande operacijaIP) {
    this.operacijaIP = operacijaIP;
  }

  @Override
  public void obradiZahtjev(String komanda) {
    operacijaIP.obradiZahtjev("IP");
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

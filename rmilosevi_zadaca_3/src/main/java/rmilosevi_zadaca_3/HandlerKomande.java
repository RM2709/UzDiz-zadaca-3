package rmilosevi_zadaca_3;

public interface HandlerKomande {

  public void obradiZahtjev(String komanda);

  public void postaviSljedeceg(HandlerKomande handler);

}

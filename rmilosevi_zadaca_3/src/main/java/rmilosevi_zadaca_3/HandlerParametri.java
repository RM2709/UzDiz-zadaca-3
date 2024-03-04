package rmilosevi_zadaca_3;

public interface HandlerParametri {

  public void obradiZahtjev(String kljucParametra, String vrijednostParametra) throws Exception;

  public void postaviSljedeceg(HandlerParametri handler);

}

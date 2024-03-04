package rmilosevi_zadaca_3;

public class HandlerCjelobrojniParametar implements HandlerParametri {

  HandlerParametri sljedeci;

  @Override
  public void obradiZahtjev(String kljucParametra, String vrijednostParametra) throws Exception {
    if (kljucParametra.compareTo("ms") == 0 || kljucParametra.compareTo("vi") == 0
        || kljucParametra.compareTo("mt") == 0) {
      try {
        if (Integer.parseInt(vrijednostParametra) <= 0) {
          throw new Exception(
              "Parametar '" + kljucParametra + "' mora biti cijeli broj veći od nula!");
        }
      } catch (Exception e) {
        throw new Exception(
            "Parametar '" + kljucParametra + "' mora biti cijeli broj veći od nula!");
      }
    } else {
      if (sljedeci != null) {
        sljedeci.obradiZahtjev(kljucParametra, vrijednostParametra);
      } else {
        throw new Exception("Parametar " + kljucParametra + " nije prepoznat.");
      }
    }
  }

  @Override
  public void postaviSljedeceg(HandlerParametri handler) {
    sljedeci = handler;
  }

}

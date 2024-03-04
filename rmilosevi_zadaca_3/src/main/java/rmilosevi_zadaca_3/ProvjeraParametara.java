package rmilosevi_zadaca_3;

import java.util.Properties;

public class ProvjeraParametara {

  public void provjeriParametre(Properties parametri, HandlerParametri prvi_handler) throws Exception {
    for (String kljucParametra : parametri.stringPropertyNames()) {
      prvi_handler.obradiZahtjev(kljucParametra, parametri.getProperty(kljucParametra));
    }
  }

}

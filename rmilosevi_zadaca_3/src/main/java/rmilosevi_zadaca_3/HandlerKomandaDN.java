package rmilosevi_zadaca_3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;

public class HandlerKomandaDN implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("DN") == 0) {
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
    String poravnajLijevo = "| %-20s | %-70s |%n";
    if (!tvrtka.getDnevnik().isEmpty()) {
      System.out.format(poravnajLijevo, "Vrijeme", "Komanda");
      for (SimpleEntry<LocalDateTime, String> zapis : tvrtka.getDnevnik()) {
        System.out.format(poravnajLijevo,
            zapis.getKey().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")),
            zapis.getValue());
      }
    } else {
      System.out.format("Dnevnik je prazan");
    }
  }


  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

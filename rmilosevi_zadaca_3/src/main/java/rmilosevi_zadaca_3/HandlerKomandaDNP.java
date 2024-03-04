package rmilosevi_zadaca_3;

import java.util.regex.Pattern;

public class HandlerKomandaDNP implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakDNP = Pattern.compile("^DNP (D|N)$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakDNP.matcher(komanda).find()) {
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
    String da_ne = komanda.trim().split(" ")[1].trim();
    if (da_ne.compareTo("D") == 0) {
      if (tvrtka.pisiDnevnik() == true) {
        System.out.println("Pisanje u dnevnik je već uključeno");
      } else {
        tvrtka.postaviPisanjeDnevnika(true);
        System.out.println("Pisanje u dnevnik uključeno");
      }
    } else {
      if (tvrtka.pisiDnevnik() == false) {
        System.out.println("Pisanje u dnevnik je već isključeno");
      } else {
        tvrtka.postaviPisanjeDnevnika(false);
        System.out.println("Pisanje u dnevnik isključeno");
      }
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

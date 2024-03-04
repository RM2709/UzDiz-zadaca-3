package rmilosevi_zadaca_3;

import java.util.regex.Pattern;

public class HandlerKomandaPS implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakPS = Pattern.compile("^PS [A-Ž]{2}[0-9]{3,4}[A-Z]{2} (A|NI|NA)$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakPS.matcher(komanda).find()) {
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
    String[] komanda_dio = komanda.split(" ");
    boolean postoji = false;
    for (Vozilo vozilo : tvrtka.getVozila()) {
      if (vozilo.getRegistracija().compareTo(komanda_dio[1]) == 0) {
        postoji = true;
        if ((vozilo.getStatus().getClass().getSimpleName().compareTo("Aktivno") == 0
            && komanda_dio[2].compareTo("A") == 0)
            || (vozilo.getStatus().getClass().getSimpleName().compareTo("NijeAktivno") == 0
                && komanda_dio[2].compareTo("NA") == 0)
            || (vozilo.getStatus().getClass().getSimpleName().compareTo("NijeIspravno") == 0
                && komanda_dio[2].compareTo("NI") == 0)) {
          System.out.println("Status vozila '" + komanda_dio[1] + "' je već jednak unesenom");
          continue;
        }
        switch (komanda_dio[2]) {
          case "A": {
            vozilo.setStatus(new Aktivno());
            break;
          }
          case "NA": {
            vozilo.setStatus(new NijeAktivno());
            break;
          }
          case "NI": {
            vozilo.setStatus(new NijeIspravno());
            break;
          }
        }
        System.out.println("Status vozila " + vozilo.getRegistracija().trim() + " postavljen na '"
            + vozilo.getStatus().getClass().getSimpleName() + "'");
      }
    }
    if (!postoji) {
      System.out.println("Vozilo s registarskom oznakom '" + komanda_dio[1] + "' nije pronađeno");
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

package rmilosevi_zadaca_3;

import java.time.format.DateTimeFormatter;

public class HandlerKomandaIP implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("IP") == 0) {
      obradi();
    } else {
      if (sljedeci != null) {
        sljedeci.obradiZahtjev(komanda);
      } else {
        System.out.println("Krivi unos komande");
      }
    }
  }

  private void obradi() {
    System.out.println("Virtualni sat: "
        + tvrtka.getVirtualni_sat().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")));
    String poravnajLijevo = "| %-15s | %-30s | %-15s | %-15s | %-15s | %-30s | %-15s | %-15s|%n";
    System.out.format(poravnajLijevo, "Oznaka", "Vrijeme prijema", "Vrsta paketa", "Usluga dostave",
        "Status isporuke", "Vrijeme preuzimanja", "Iznos dostave", "Iznos pouzeća");

    for (Paket paket : tvrtka.getUredZaPrijem().dohvatiEvidencijuPaketa()) {
      if (paket.getStatus_isporuke().compareTo("Nezaprimljen") != 0) {
        System.out.format(poravnajLijevo, paket.getOznaka(),
            paket.getVrijeme_prijema().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")),
            paket.getVrsta().oznaka(), paket.getUsluga_dostave(), paket.getStatus_isporuke(),
            (paket.getVrijeme_preuzimanja() != null) ? paket.getVrijeme_preuzimanja()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")) : "Nije dostavljen",
            String.format("%.2f", (paket.getIznos_dostave())),
            String.format("%.2f", (paket.getIznos_pouzeca())));
      }
    }
  }


  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

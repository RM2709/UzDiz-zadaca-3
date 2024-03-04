package rmilosevi_zadaca_3;

import java.util.regex.Pattern;

public class HandlerKomandaVV implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakVV = Pattern.compile("^VV [A-Ž]{2}[0-9]{3,4}[A-Z]{2}$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakVV.matcher(komanda).find()) {
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
    String poravnajLijevo =
        "| %-20s | %-20s | %-15s | %-13s | %-15s | %-15s | %-17s | %-10s | %-10s |%n";
    boolean postoji = false;

    for (Vozilo vozilo : tvrtka.getUredZaDostavu().getVozila()) {
      if (vozilo.getRegistracija().compareTo(komanda.split(" ")[1].trim()) == 0) {
        postoji = true;
        if (vozilo.getStatus().getClass().getSimpleName().compareTo("Aktivno") == 0) {
          System.out.format(poravnajLijevo, "Vrijeme početka", "Vrijeme povratka", "Trajanje (min)",
              "Odvoženo km", "Hitni paketi", "Obični paketi", "Isporučeni paketi", "% težine",
              "% prostora");
          for (Voznja voznja : vozilo.getVoznje()) {
            voznja.izracunajPostotakProstora(vozilo);
            voznja.izracunajPostotakTezine(vozilo);
            tvrtka.getIspisVisitor().posjetiVoznju(voznja);
          }
        } else if (vozilo.getStatus().getClass().getSimpleName().compareTo("NijeAktivno") == 0) {
          System.out.println(
              "Vozilo " + vozilo.getRegistracija() + " " + vozilo.getOpis() + " je neaktivno!");
        } else {
          System.out.println(
              "Vozilo " + vozilo.getRegistracija() + " " + vozilo.getOpis() + " je neispravno!");
        }
      }
    }
    if (!postoji) {
      System.out.println("Vozilo s oznakom '" + komanda.split(" ")[1].trim() + "' ne postoji");
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

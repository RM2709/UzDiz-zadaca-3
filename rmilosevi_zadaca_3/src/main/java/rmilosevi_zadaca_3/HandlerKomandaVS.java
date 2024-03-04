package rmilosevi_zadaca_3;

import java.util.regex.Pattern;

public class HandlerKomandaVS implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakVS = Pattern.compile("^VS [A-Ž]{2}[0-9]{3,4}[A-Z]{2} [0-9]{1,}$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakVS.matcher(komanda).find()) {
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
    String[] komanda_split = komanda.split(" ");
    Integer broj_voznje = Integer.parseInt(komanda_split[2].trim());
    boolean postoji = false;
    String poravnajLijevo = "| %-20s | %-20s | %-15s | %-13s | %-15s |%n";
    for (Vozilo vozilo : tvrtka.getUredZaDostavu().getVozila()) {
      if (vozilo.getRegistracija().compareTo(komanda_split[1].trim()) == 0) {
        postoji = true;
        if (vozilo.getStatus().getClass().getSimpleName().compareTo("Aktivno") == 0) {
          if (vozilo.brojVoznji() >= broj_voznje && broj_voznje > 0) {
            System.out.format(poravnajLijevo, "Vrijeme početka", "Vrijeme kraja", "Trajanje (min)",
                "Odvoženo km", "Paket");
            for (SegmentVoznje segmentVoznje : vozilo.getVoznje().get(broj_voznje - 1)
                .getSegmentiVoznje()) {
              tvrtka.getIspisVisitor().posjetiSegment(segmentVoznje);
            }
          } else {
            System.out.println("Vožnja s brojem '" + broj_voznje + "' ne postoji");
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
      System.out.println("Vozilo s oznakom '" + komanda_split[1].trim() + "' ne postoji");
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

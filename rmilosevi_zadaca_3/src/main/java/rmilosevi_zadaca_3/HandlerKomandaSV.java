package rmilosevi_zadaca_3;

public class HandlerKomandaSV implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();

  @Override
  public void obradiZahtjev(String komanda) {
    if (komanda.compareTo("SV") == 0) {
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
        "| %-12s | %-20s | %-15s | %-13s | %-15s | %-15s | %-17s | %-10s | %-10s | %-12s |%n";
    System.out.format(poravnajLijevo, "Oznaka", "Naziv", "Status", "Odvoženi km", "Hitni paketi",
        "Obični paketi", "Isporučeni paketi", "% težine", "% prostora", "Broj vožnji");
    for (Vozilo vozilo : tvrtka.getUredZaDostavu().getVozila()) {
      tvrtka.getIspisVisitor().posjetiVozilo(vozilo);
    }
  }


  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

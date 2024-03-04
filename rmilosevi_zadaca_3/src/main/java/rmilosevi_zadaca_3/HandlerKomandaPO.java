package rmilosevi_zadaca_3;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Pattern;

public class HandlerKomandaPO implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakPO = Pattern.compile("^PO '[ -ž]{1,}' [0-Ž]{9} (D|N)$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakPO.matcher(komanda).find()) {
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
    String[] komanda_dio = komanda.split("'");
    String string_osoba = komanda_dio[1].trim();
    String string_paket = komanda_dio[2].trim().split(" ")[0].trim();
    Osoba odabrana_osoba = null;
    Paket odabrani_paket = null;
    for (Osoba osoba : tvrtka.getOsobe()) {
      if (osoba.getNaziv().compareTo(string_osoba) == 0) {
        odabrana_osoba = osoba;
      }
    }
    if (odabrana_osoba == null) {
      System.out.println("Odabrana osoba ne postoji");
      return;
    }
    for (Paket paket : tvrtka.getUredZaPrijem().dohvatiEvidencijuPaketa()) {
      if (paket.getOznaka().compareTo(string_paket) == 0) {
        odabrani_paket = paket;
      }
    }
    if (odabrani_paket == null) {
      if (!postojiNezaprimljeniPaket(string_paket)) {
        System.out.println("Odabrani paket ne postoji");
      } else {
        prijaviOdjaviSlusanjeNezaprimljenogPaketa(komanda_dio[2].trim().split(" ")[1], string_osoba,
            string_paket);
      }
      return;
    }
    if (odabrana_osoba != odabrani_paket.getPosiljatelj()
        && odabrana_osoba != odabrani_paket.getPrimatelj()) {
      System.out.println("Odabrana osoba nije primatelj niti pošiljatelj odabranog paketa");
      return;
    }
    prijaviOdjaviSlusanje(komanda_dio, odabrana_osoba, odabrani_paket);
  }

  private void prijaviOdjaviSlusanjeNezaprimljenogPaketa(String d_n, String string_osoba,
      String string_paket) {
    if (d_n.compareTo("D") == 0) {
      prijaviSlusanjeNezaprimljenogPaketa(string_paket, string_osoba);
    } else if (d_n.compareTo("N") == 0) {
      odjaviSlusanjeNezaprimljenogPaketa(string_paket, string_osoba);
    }
  }

  private void odjaviSlusanjeNezaprimljenogPaketa(String string_paket, String string_osoba) {
    for (SimpleEntry<LocalDateTime, String> podatak : tvrtka.getPodaci_paketi()) {
      if (podatak.getValue().split(";")[0].trim().compareTo(string_paket) == 0) {
        if (podatak.getValue().split(";")[2].trim().compareTo(string_osoba) == 0) {
          if (tvrtka.getOdjave_slusanja()
              .contains(new SimpleEntry<String, String>(string_paket, string_osoba))) {
            System.out.println("Pošiljatelj '" + string_osoba
                + "' već ne prima obavijesti o paketu " + string_paket);
          } else {
            tvrtka
                .dodajOdjavu_slusanja(new SimpleEntry<String, String>(string_paket, string_osoba));
            System.out.println("Odjavljeno slušanje paketa " + string_paket + " za pošiljatelja '"
                + string_osoba + "'");
          }
          return;
        } else if (podatak.getValue().split(";")[3].trim().compareTo(string_osoba) == 0) {
          if (tvrtka.getOdjave_slusanja()
              .contains(new SimpleEntry<String, String>(string_paket, string_osoba))) {
            System.out.println("Primatelj '" + string_osoba + "' već ne prima obavijesti o paketu "
                + string_paket);
          } else {
            tvrtka
                .dodajOdjavu_slusanja(new SimpleEntry<String, String>(string_paket, string_osoba));
            System.out.println("Odjavljeno slušanje paketa " + string_paket + " za primatelja '"
                + string_osoba + "'");
          }
          return;
        }
      }
    }
    System.out.println("Odabrana osoba nije primatelj niti pošiljatelj odabranog paketa");

  }

  private void prijaviSlusanjeNezaprimljenogPaketa(String string_paket, String string_osoba) {
    for (SimpleEntry<LocalDateTime, String> podatak : tvrtka.getPodaci_paketi()) {
      if (podatak.getValue().split(";")[0].trim().compareTo(string_paket) == 0) {
        if (podatak.getValue().split(";")[2].trim().compareTo(string_osoba) == 0) {
          if (!tvrtka.getOdjave_slusanja()
              .contains(new SimpleEntry<String, String>(string_paket, string_osoba))) {
            System.out.println(
                "Pošiljatelj '" + string_osoba + "' već prima obavijesti o paketu " + string_paket);
          } else {
            tvrtka
                .ukloniOdjavu_slusanja(new SimpleEntry<String, String>(string_paket, string_osoba));
            System.out.println("Prijavljeno slušanje paketa " + string_paket + " za pošiljatelja '"
                + string_osoba + "'");
          }
          return;
        } else if (podatak.getValue().split(";")[3].trim().compareTo(string_osoba) == 0) {
          if (!tvrtka.getOdjave_slusanja()
              .contains(new SimpleEntry<String, String>(string_paket, string_osoba))) {
            System.out.println(
                "Primatelj '" + string_osoba + "' već prima obavijesti o paketu " + string_paket);
          } else {
            tvrtka
                .ukloniOdjavu_slusanja(new SimpleEntry<String, String>(string_paket, string_osoba));
            System.out.println("Prijavljeno slušanje paketa " + string_paket + " za primatelja '"
                + string_osoba + "'");
          }
          return;
        }
      }
    }
    System.out.println("Odabrana osoba nije primatelj niti pošiljatelj odabranog paketa");
  }

  private boolean postojiNezaprimljeniPaket(String string_paket) {
    for (SimpleEntry<LocalDateTime, String> podatak : tvrtka.getPodaci_paketi()) {
      if (podatak.getValue().split(";")[0].trim().compareTo(string_paket) == 0) {
        return true;
      }
    }
    return false;
  }

  private void prijaviOdjaviSlusanje(String[] komanda_dio, Osoba odabrana_osoba,
      Paket odabrani_paket) {
    if (komanda_dio[2].trim().split(" ")[1].compareTo("D") == 0) {
      odabrani_paket.paketObavijestavanje.prijaviSlusanje(odabrana_osoba);
      System.out.println("Prijavljeno slušanje paketa " + odabrani_paket.getOznaka() + " za "
          + ((odabrana_osoba == odabrani_paket.getPrimatelj() ? "primatelja" : "pošiljatelja"))
          + " '" + odabrana_osoba.getNaziv() + "'");
    } else if (komanda_dio[2].trim().split(" ")[1].compareTo("N") == 0) {
      odabrani_paket.paketObavijestavanje.odjaviSlusanje(odabrana_osoba);
      System.out.println("Odjavljeno slušanje paketa " + odabrani_paket.getOznaka() + " za "
          + ((odabrana_osoba == odabrani_paket.getPrimatelj() ? "primatelja" : "pošiljatelja"))
          + " '" + odabrana_osoba.getNaziv() + "'");
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

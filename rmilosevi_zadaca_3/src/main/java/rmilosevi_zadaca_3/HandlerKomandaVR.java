package rmilosevi_zadaca_3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class HandlerKomandaVR implements HandlerKomande {

  HandlerKomande sljedeci;
  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  Pattern uzorakVR = Pattern.compile("^VR [1-9]{1}[0-9]{0,1}$");

  @Override
  public void obradiZahtjev(String komanda) {
    if (uzorakVR.matcher(komanda).find()) {
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
    int broj_sati = Integer.parseInt(komanda.split(" ")[1]);
    LocalDateTime ciljano_vrijeme = tvrtka.getVirtualni_sat().plusHours(broj_sati);
    LocalDateTime pocetak;
    LocalDateTime kraj;
    boolean kraj_smjene = false;
    while (tvrtka.getVirtualni_sat().isBefore(ciljano_vrijeme)) {
      spavaj();
      pocetak = tvrtka.getVirtualni_sat().plusSeconds(0);
      if (tvrtka.getVirtualni_sat().plusSeconds(tvrtka.getMnozitelj_sekundi())
          .isAfter(ciljano_vrijeme)) {
        tvrtka.setVirtualni_sat(ciljano_vrijeme.plusSeconds(0));
      } else {
        tvrtka
            .setVirtualni_sat(tvrtka.getVirtualni_sat().plusSeconds(tvrtka.getMnozitelj_sekundi()));
      }
      kraj = tvrtka.getVirtualni_sat().plusSeconds(0);
      if (!pocetak.toLocalTime().plusSeconds(1).isAfter(tvrtka.getPocetak_rada())
          && kraj.toLocalTime().minusSeconds(1).isAfter(tvrtka.getPocetak_rada())) {
        pocetak = tvrtka.getPocetak_rada().atDate(pocetak.toLocalDate());
      }
      if (!kraj.toLocalTime().minusSeconds(1).isBefore(tvrtka.getKraj_rada())
          && pocetak.toLocalTime().minusSeconds(1).isBefore(tvrtka.getKraj_rada())) {
        kraj = tvrtka.getKraj_rada().atDate(pocetak.toLocalDate());
        kraj_smjene = true;
      }
      if (pocetak.plusSeconds(1).toLocalTime().isAfter(tvrtka.getPocetak_rada())
          && kraj.minusSeconds(1).toLocalTime().isBefore(tvrtka.getKraj_rada())) {
        tvrtka.getUredZaPrijem().rad(pocetak, kraj);
        tvrtka.getUredZaDostavu().rad(pocetak, kraj,
            tvrtka.getUredZaPrijem().dohvatiEvidencijuPaketa(), true);
      } else if (pocetak.plusSeconds(1).toLocalTime().isAfter(tvrtka.getPocetak_rada())
          && !kraj.minusSeconds(1).toLocalTime().isBefore(tvrtka.getKraj_rada())) {
        tvrtka.getUredZaDostavu().rad(pocetak, kraj,
            tvrtka.getUredZaPrijem().dohvatiEvidencijuPaketa(), false);
      }
      System.out.println(
          tvrtka.getVirtualni_sat().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")));
    }
    System.out
        .println("Do kraja rada je došlo zbog " + ((!kraj_smjene) ? "isteka vremena izvršavanja"
            : "kraja radnog vremena u " + tvrtka.getKraj_rada()));
  }

  private void spavaj() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void postaviSljedeceg(HandlerKomande handler) {
    sljedeci = handler;
  }

}

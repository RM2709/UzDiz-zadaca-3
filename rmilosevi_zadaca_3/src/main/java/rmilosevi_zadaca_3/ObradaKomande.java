package rmilosevi_zadaca_3;

public class ObradaKomande implements ServisObradeKomande {

  Tvrtka tvrtka = Tvrtka.dohvatiInstancu();
  HandlerKomande prvi_handler;

  public ObradaKomande() {
    HandlerKomande handlerIP = new HandlerKomandaIP();
    HandlerKomande handlerVR = new HandlerKomandaVR();
    HandlerKomande handlerDN = new HandlerKomandaDN();
    HandlerKomande handlerDNP = new HandlerKomandaDNP();
    HandlerKomande handlerPO = new HandlerKomandaPO();
    HandlerKomande handlerPP = new HandlerKomandaPP();
    HandlerKomande handlerPS = new HandlerKomandaPS();
    HandlerKomande handlerSV = new HandlerKomandaSV();
    HandlerKomande handlerVS = new HandlerKomandaVS();
    HandlerKomande handlerVV = new HandlerKomandaVV();
    HandlerKomande handlerQ = new HandlerKomandaQ();
    HandlerKomande handlerIPP = new HandlerKomandaIPP(handlerIP);
    HandlerKomande handlerSPV = new HandlerKomandaSPV();
    HandlerKomande handlerPPV = new HandlerKomandaPPV();
    handlerIP.postaviSljedeceg(handlerVR);
    handlerVR.postaviSljedeceg(handlerDN);
    handlerDN.postaviSljedeceg(handlerDNP);
    handlerDNP.postaviSljedeceg(handlerPO);
    handlerPO.postaviSljedeceg(handlerPP);
    handlerPP.postaviSljedeceg(handlerPS);
    handlerPS.postaviSljedeceg(handlerSV);
    handlerSV.postaviSljedeceg(handlerVS);
    handlerVS.postaviSljedeceg(handlerVV);
    handlerVV.postaviSljedeceg(handlerQ);
    handlerQ.postaviSljedeceg(handlerIPP);
    handlerIPP.postaviSljedeceg(handlerSPV);
    handlerSPV.postaviSljedeceg(handlerPPV);
    handlerPPV.postaviSljedeceg(null);
    prvi_handler = handlerIP;
  }

  @Override
  public void obradiKomandu(String komanda) {
    prvi_handler.obradiZahtjev(komanda);
  }
}

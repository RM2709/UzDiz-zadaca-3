package rmilosevi_zadaca_3;

public interface Visitor {

  public void posjetiVozilo(Vozilo vozilo);

  public void posjetiVoznju(Voznja voznja);

  public void posjetiSegment(SegmentVoznje segment);

}

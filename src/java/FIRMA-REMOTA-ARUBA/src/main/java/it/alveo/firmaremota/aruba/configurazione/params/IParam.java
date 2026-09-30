package it.alveo.firmaremota.aruba.configurazione.params;

public interface IParam {

    String getChiave();

    String getDescrizione();

    String getValore();

    boolean isObbligatorio();
}

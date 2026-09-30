package it.gruppoinit.pal.gp.core.service;

public interface DocumentazioneIstanzaHelperService {

    /**
     * Crea un file di export csv di tutti i documenti associati alla pratica (
     * documentiistanza,movimentiallegati,allegatiendo,documentiprocure, documento delle schede dinamiche
     */
    public byte[] exportCsv(Integer codiceIstanza, String nameFileOutput);
}

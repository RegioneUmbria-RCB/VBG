package it.gruppoinit.pal.gp.core.service;

public interface ElaborazioneDizionariCart {

    void elaboraDizionario(String idente, String idcomunealias, String software, String descrizioneente);

    void aggiungiInterventi(String idente, String idcomunealias, String software, String descrizioneente);

    void aggiornaSchedeEndo2(String idente, String idcomunealias, String software, String descrizioneente);
}

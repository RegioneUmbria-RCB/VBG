package it.gruppoinit.pal.gp.core.features.istanze.metadati;

import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;

public interface IIstanzeMetadatiService {

    void deleteByIstanza(Integer codiceIstanza);

    void aggiornaMetadatoStatoIstanza(Integer codiceIstanza);

    void insert(IstanzeMetadati istanzeMetadati);
}

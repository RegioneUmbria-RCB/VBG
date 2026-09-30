package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

import it.gruppoinit.pal.gp.core.domain.Oggetti;

public interface IApplicaQRCodeService {

    void applicaQRCode(Integer codice, Oggetti o, boolean isIstanza);
}

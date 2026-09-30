package it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione;

import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipoMittDestAutoEnum;

public interface IVerticalizzazioneProtocolloAttivoService {

    String nomeVerticalizzazione();

    boolean isAttiva();

    String isAttivoApplicaLayer();

    boolean isForzaFascicolazioneNotificaAutomatica();

    Integer getGestionePEC();

    String getTipoMovRicevuta();

    TipoMittDestAutoEnum getTipoMittDestAuto();

    TipoMittDestAutoEnum getTipoMittDestAuto(String software);

    String getFlussoDefault();

    String getFlussoDefault(String software);

    String getMappaturaDittaIndividuale();

    String getMappaturaDittaIndividuale(String software);

    Integer getCodiceAmministrazioneDefault();

    ProtocolloMezzi getMezzoDefault();

    ProtocolloModalitainvio getModalitaTrasmissioneDefault();

    String getTipoDocumentoDefault();

    String getTipoDocumentoDefaultBo();

    String getTipoSmistamentoDefault();

    boolean trasformaOggettoProtocolloUpperCase();

    Integer lunghezzaMassimaOggettoProtocollo();

    String getClassificaDefaultBO();
}

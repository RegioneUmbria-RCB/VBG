package it.gruppoinit.pdd.ri.service;

import java.math.BigInteger;

import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.DatiRispostaREA;
import it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione.IscrizioneImpresaRiSpcResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.getpraticasuap.CompilaPraticaSUAPXMLResponseType;
import it.gruppoinit.pdd.ri.service.impl.VERSIONE_PRATICA_SUAP;
import it.gruppoinit.pdd.utils.TIPO_PRATICA;
import it.gruppoinit.wsanagrafe2.schema.Anagrafe;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;

public interface RegistroImpreseService {

    public enum TIPO_NOTIFICA_ENUM {
	AVVIO,
	ESITO
    }

    public IscrizioneImpresaRiSpcResponse findDatiImpresa(String idcomunealias, String idComune, String codiceFiscale);

    public DatiRispostaREA notificaComunicazioneREA(InserimentoAttivitaNLARequest request, TIPO_NOTIFICA_ENUM tipoNotifica);

    /**
     * Schema ConversioneUtils per convertire un'oggetto IscrizioneImpresaRiSpcResponse in Anagrafe
     * 
     * @param response
     * @return
     */
    public Anagrafe iscrizioneImpresaToAnagrafe(IscrizioneImpresaRiSpcResponse response) throws Exception;

    public CompilaPraticaSUAPXMLResponseType compilaPraticaSUAP(String token, BigInteger codiceistanza);

    public String compilaPraticaSUAPComeStringa(String idComuneAlias, BigInteger codiceistanza, boolean isEffettuaValidazione,
	    VERSIONE_PRATICA_SUAP versionePraticaSuap, TIPO_PRATICA tipoPRATICA);
}

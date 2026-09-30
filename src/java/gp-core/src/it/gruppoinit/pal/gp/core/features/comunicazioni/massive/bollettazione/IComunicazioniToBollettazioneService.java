package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.LetteraGenerataPerComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

public interface IComunicazioniToBollettazioneService {

    public void collegaBollettazioneAComunicazioni(int idTestata, int idBollettazione);

    public void collegaDettaglioBollettazioneADettaglioComunicazioni(int idDettaglioComunicazione, int idDettaglioBollettazione);

    public List<DettaglioBollettazione> getDettagli(FiltriRicercaDettagli filtri);

    int generaOggettoAccompagnamentoBollettazioneDettaglio(int codiceLettera, int idDettaglioMassiva, boolean trasformaInPdf);

    public List<Integer> recuperaAvvisoDiPagamento(Integer idDettaglioMassiva) throws FunzioneBusinessRemotaException;

    public ComunicazioneBollettazione getComunicazioneBollettazione(FiltriRicercaTestata filtri);

    public List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(Integer bollGestTestataId);

    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione);

    public LetteraGenerataPerComunicazione generaLetteraAccompagnamentoBollettazioneDettaglio(int codiceLettera, int idDettaglioMassiva,
	    boolean trasformaInPdf);
}

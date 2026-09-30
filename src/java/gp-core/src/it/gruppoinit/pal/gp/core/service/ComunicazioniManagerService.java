package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;

import java.util.List;

public interface ComunicazioniManagerService {

    public void insertInizializzazioniStep(Integer codice);

    //    public int insertStepMovimento(ComunicazioniT comunicazioniT, ComunicazioniD comunicazionid, Istanze istanza);
    //
    //    public int insertStepProtocolloMovimento(ComunicazioniT comunicazioniT, ComunicazioniD comunicazionid, Istanze istanza, Movimenti movimento,
    //	    PassoCreazioneComunicazioneEnum passoCreazioneComunicazioneEnum);
    //
    //    public int insertStepMovimentoAllegato(ComunicazioniT comunicazioniT, ComunicazioniD comunicazionid, Istanze istanza, Movimenti movimento);
    //
    //    public int insertStepconversioneInPDFAllegatoMovimento(ComunicazioniT comunicazioniT, ComunicazioniD comunicazioniD, Istanze istanza,
    //	    Movimenti movimento);
    //
    //    public int inserimentoMailInviataDalMovimentoPerLaComunicazione(ComunicazioniD comunicazionid, ComunicazioniT comunicazioniT, Istanze istanza,
    //	    Movimenti movimento, String destinatarioA, String destinatarioCc);
    //
    //    public int inserimentoMettiAllaFirmaPerLaComunicazione(ComunicazioniT comunicazioniT, ComunicazioniD comunicazioniD, Istanze istanza,
    //	    Movimenti movimenti);
    public Istanze findIstanzaById(Integer codiceistanza);

    public ComunicazioniD findComunicazioniDById(Integer codicecomunicazioned);

    public ComunicazioniT findComunicazioniTById(Integer codicecomunicazionet);

    public void updateComunicazioniT(ComunicazioniT comunicazioniT);

    public void insertComunicazioniT(ComunicazioniT comunicazioniT);

    public void insertComunicazioneD(ComunicazioniD comunicazionid);

    public TipoComunicazioniT findTipoComunicazioniById(String codiceTipoComunicazione);

    public List<TmpStatiComunicazioniD> findStepNonEseguiti(Integer codiceComunicazioneD);

    public int eseguiStep(String stato, ComunicazioniD comunicazioniD, Istanze istanza, ComunicazioniT comunicazioniT,
	    ComunicazioniDHelper comunicazioniDHelper);

    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum);

    public void updateStatoComunicazioneD(Integer codiceComunicazioneD, ComunicazioniDStatoEnum comunicazioniDStatoEnum);

    public boolean exsistComunicazioniDNonTerminate(Integer codiceComunicazioneT);

    public void checkComunicazioniBloccateSuInvioEmail(Integer codiceCominicazioneT);

    public void flush();

    public void commit();

    public void clear();
}

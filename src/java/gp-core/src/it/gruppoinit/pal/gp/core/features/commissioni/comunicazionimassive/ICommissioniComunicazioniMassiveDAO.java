package it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.CommedilizieMassiveT;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ComunicazioneCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.DettaglioRigaCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaDettagliCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaTestataCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;

public interface ICommissioniComunicazioniMassiveDAO {

    void collegaCommissioniAComunicazioni(int idTestata, int idCommissioni);

    void collegaDettaglioCommissioniADettaglioComunicazioni(int idDettaglioComunicazione, int idAppelloCommissioni);

    List<DettaglioRigaCommissione> getDettagli(FiltriRicercaDettagliCommissioni filtri);

    public ComunicazioneCommissione getComunicazioneCommissioni(FiltriRicercaTestataCommissioni filtri);

    List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(int idDettaglioComunicazione);

    List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioCommissione(Integer idCommissione);

    public boolean sonoPresentiComunicazioni(Integer idCommissione);

    boolean exists(Integer idTestata);

    CommedilizieMassiveT findByIdTestata(int idTestata);
}

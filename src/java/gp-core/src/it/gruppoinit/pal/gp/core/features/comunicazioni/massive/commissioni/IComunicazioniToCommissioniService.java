package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

public interface IComunicazioniToCommissioniService {

    public void collegaCommissioneAComunicazioni(int idTestata, int idCommissioni);

    public void collegaDettaglioCommissioneADettaglioComunicazioni(int idDettaglioComunicazione, int idAppelloCommissioni);

    public ComunicazioneCommissione getComunicazioneCommissione(FiltriRicercaTestataCommissioni filtri);

    public List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(Integer commissioniMassiveTestataId);

    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione);

    public List<DettaglioRigaCommissione> getDettagli(FiltriRicercaDettagliCommissioni filtri);

    public int generaLetteraAccompagnamentoCommissioniDettaglio(int codiceLettera, int idDettaglioMassiva, boolean trasformaInPdf);
}

package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

public interface IComunicazioniToGenService {

    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);

    void collegaDettaglioMercatoADettaglioComunicazioni(Integer codice, Map<Integer, List<DettaglioRigaGen>> m);

    int generaLetteraAccompagnamentoCommissioniDettaglio(int codiceLettera, int idRigaDettaglioMassiva, boolean convertiInPdf, Integer codiceistanza);

    List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen);

    List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen,
	    List<ISoftwareComuneData> softwareComuneData);

    public List<DettaglioRigaGen> getDettagli(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen);

    public void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);

    void collegaDettaglioIstanzeADettaglioComunicazioni(Integer codice, Set<Integer> idistanze, boolean isMovimenti, String tipiMovimento,
	    Integer codiceAmministrazione);

    public List<ISoftwareComuneData> getSoftwareAndComune(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen);
}

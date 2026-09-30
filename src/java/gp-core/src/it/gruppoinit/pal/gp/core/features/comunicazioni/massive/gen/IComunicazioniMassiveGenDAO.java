package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveD;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveDIstanze;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveD;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;

public interface IComunicazioniMassiveGenDAO {
    
    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);

    void collegaDettaglioMercatoADettaglioComunicazioni(Integer codice, Map<Integer, List<DettaglioRigaGen>> m);
    
    public boolean existsTestata(String sql, Integer idTestata);

    List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen);

    public List<DettaglioRigaGen> getDettagli(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen);

    Integer getIdMercatoByTestata(Integer idTestata);

    void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);

    List<SoftwareComuneDataBean> getMercatoDettaglioDByIdDett(Integer idmassivedettaglio);

    void collegaDettaglioIstanzeADettaglioComunicazioni(Integer codice, Set<Integer> idistanze, boolean isMovimenti, String tipiMovimento,
	    Integer codiceAmministrazione);

    List<IstanzeMassiveDIstanze> findIstanzeMassiveDIstanze(Integer idmassivedettaglio);

    void saveMovimento(IstanzeMassiveDIstanze istanzeMassiveDIstanza, Tipimovimento tipomovimento, Amministrazioni amministrazioni,
	    Responsabili responsabile, Istanze istanza);

    List<Istanze> getIstanzeDettaglioDByIdDett(Integer idmassivedettaglio);

    String getGroupTypeForIstanze(int idtestata);

    Istanze getIstanzaFromDettaglio(int idDettaglioComunicazione);

    List<Integer> getAutorizzazioniFromDettaglio(int idDettaglioComunicazione);

    List<Istanze> getIstanzeListFromDettaglio(int idDettaglioComunicazione);

}

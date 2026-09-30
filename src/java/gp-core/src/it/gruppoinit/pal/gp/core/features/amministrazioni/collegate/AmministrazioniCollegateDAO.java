package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegate;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegateId;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmmCollComuneBean;

public interface AmministrazioniCollegateDAO extends BaseDAO<AmministrazioniCollegate, AmministrazioniCollegateId> {

    public Amministrazioni findCollegataByAmministrazioneAndComune(Integer codiceAmministrazione, String codiceComune);

    /**
     * Torna la lista dei comuni, presa da comuniassociati che non sono stati ancora usati per questa
     * amministrazionemadre
     * 
     * @param codiceAmministrazione
     * @return
     */
    public List<AmmCollComuneBean> comuniDisponibili(Integer codiceAmministrazione);
}

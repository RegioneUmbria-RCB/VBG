package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestDettaglioDAO;

/**
 * 
 * @author
 */
public interface BollGestDettaglioService extends BaseService<BollGestDettaglio, PkId> {

    /**
     * @see BollGestDettaglioDAO#findAll(Integer, Integer)
     */
    public List<BollGestDettaglio> findAll(Integer firstResult, Integer maxResult);

    public String getComune(Integer idRigaDettaglio);

    public String exportModalitaPentaho(Integer idBollettazione, Esportazioni esportazioni, String email, boolean isInvioMail);

    public void verificaConfigurazioniCausali(Set<Integer> idRigheDettaglioBollettazione) throws InvalidConfigurationException;

    public Map<Integer, Set<String>> getComuniPerDettagliBollettazione(int idTestataBollettazione);

    public String recuperaMappaturaNodoPagDaIdDettaglio(Integer idRigaDettaglio);

    public String findArrotondamentoByBollettazione(Integer idBollettazione);
}

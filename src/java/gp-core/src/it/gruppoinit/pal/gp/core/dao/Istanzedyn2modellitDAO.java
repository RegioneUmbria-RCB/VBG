package it.gruppoinit.pal.gp.core.dao;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface Istanzedyn2modellitDAO extends BaseDAO<Istanzedyn2modellit, Istanzedyn2modellitId> {

    /**
     * Torna la lista di record legati ad un istanza
     * 
     * @param idIstanza
     *            il pkid che rappresenta l'istanza
     * @return
     */
    public List<Istanzedyn2modellit> findByIstanza(PkId idIstanza);

    public List<Integer> findIdSchedeByIstanza(Integer idIstanza);

    public Set<Integer> schedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaRicercare);

    public void insert(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaAggiungere);

    public List<Integer> findIdModelloByIstanzaAndIdCampo(Integer codiceIstanza, Integer idDyn2Campi);
}

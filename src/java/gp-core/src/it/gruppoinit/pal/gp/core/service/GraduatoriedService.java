/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.web.GraduatoriedFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;

import java.util.List;
import java.util.Set;

/**
 * @author lucap
 * 
 */
public interface GraduatoriedService extends BaseService<Graduatoried, PkId> {

    public List<Istanzedyn2dati> findBandoOutput(Graduatoried graduatoried);

    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet);

    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum, Integer firstResult,
	    Integer maxResult);

    /**
     * @see GraduatoriedDAO#findGraduatoriedPerComunuicazione(Integer codicegraduatoriet,Integer posizioneDa,Integer
     *      posizioneA, String destinatari, List<SchedaDinamicaRigheFilter> righeFilters)
     */
    public Set<GraduatoriedDTO> findGraduatoriedPerComunuicazione(Integer codicegraduatoriet, Integer posizioneDa, Integer posizioneA,
	    String destinatari, SchedaDinamicaFilter dinamicaFilter);

    public int findExsistGraduatoridFilterByDynDatiIstanza(GraduatoriedFilter filter);

    public GraduatoriedDTO populateListaCampigraduatoria(GraduatoriedDTO graduatoried, IstanzeDTO istanza);
}

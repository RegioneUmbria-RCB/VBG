package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;

import java.util.List;

public interface GraduatorietDAO extends BaseDAO<Graduatoriet, PkId> {

    public List findListaDyn2DatiPerGraduatoria(Graduatoriet entity, Integer[] ordinamentoAscDesc, Object[] values);

    /**
     * metodo per la generazione di una nuova graduatoria questo metodo inserisce n record in GRADUATORIAD e
     * CAMPIGRADUATORIA in base agli algoritmi di selezione ordinamento e distribuzione individuati. La selezione dell'
     * istanza viene fatta sull'unico albero proc configuatato sul bando
     * 
     * @param entity
     */
    public void compilaGraduatoriaSingoloIntervento(Graduatoriet entity);

    /**
     * metodo per il recupero della lista di graduatoriet con filtro bandi.id.codice=entity.bandi.id
     * 
     * @param entity
     * @return
     */
    public List<Graduatoriet> findByFilter(Graduatoriet entity);

    /**
     * metodo per la generazione di una nuova graduatoria questo metodo inserisce n record in GRADUATORIAD e
     * CAMPIGRADUATORIA in base agli algoritmi di selezione ordinamento e distribuzione individuati. La selezione dell'
     * istanza viene fatta su gli N albero proc configuatati sul bando
     * 
     * @param entity
     */
    public List findListaDyn2DatiPerGraduatoriaPerIntervento(Graduatoriet entity, Integer[] ordinamentoAscDesc, Object[] values,
	    Integer codiceIntervento);

    public void updateDistribuisciValoriGraduatorie(Graduatoriet entity);

    public List<Tipibandooutput> findTipiBandiOutput(Integer tipiGraduatorieId);

    public List<Bandiinput> findTipiBandiInput(Integer bandiId);
}

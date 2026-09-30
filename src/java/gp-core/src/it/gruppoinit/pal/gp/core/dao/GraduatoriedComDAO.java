package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface GraduatoriedComDAO extends BaseDAO<GraduatoriedCom, PkId> {

    public List<GraduatoriedCom> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna il numero di record della tabella GraduatorietCom filtando per idcomune e FK_GT_ID = GRADUATORIET_COM.ID
     * 
     * @return
     */
    public Integer countGraduatoriedComDomande(Integer codiceGraduatoriatCom);

    /**
     * Ritorna il numero di record della tabella GraduatorietCom filtando per idcomune e FK_GT_ID = GRADUATORIET_COM.ID
     * e GRADUATORIED_COM.CODICEMOVIMENTO NOT NULL
     * 
     * @return
     */
    public Integer countGraduatoriedComMovimenti(Integer codiceGraduatoriatCom);

    /**
     * Ritorna il numero di record della tabella GraduatorietCom filtando per idcomune,FK_GT_ID = GRADUATORIET_COM.ID e
     * GRADUATORIED_COM.IDALLEGATO NOT NULL
     * 
     * @return
     */
    public Integer countGraduatoriedComAllegati(Integer codiceGraduatoriatCom);

    /**
     * Ritorna il numero di record della tabella GraduatorietCom filtando per idcomune, FK_GT_ID = GRADUATORIET_COM.ID e
     * GRADUATORIED_COM.IDMAIL NOT NULL
     * 
     * @return
     */
    public Integer countGraduatoriedComMailInviate(Integer codiceGraduatoriatCom);

    /**
     * Lista di GraduatoriedCom filtarte per il codice della testata codiceGraduatoriatCom
     * 
     * @param codiceGraduatoriatCom
     * @return
     */
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatCom(Integer codiceGraduatoriatCom, Integer firstResult, Integer maxResult);
}

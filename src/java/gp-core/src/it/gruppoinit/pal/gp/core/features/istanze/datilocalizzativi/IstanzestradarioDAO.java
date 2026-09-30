package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzestradarioDTO;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;

/**
 * 
 * @author francescop
 */
public interface IstanzestradarioDAO extends BaseDAO<Istanzestradario, PkId> {

    public Istanzestradario findPrimarioByCodiceIstanza(Integer codiceIstanza);

    /**
     * Trova le istanzestradario di una istanza ordinate per primario DESC
     * 
     * @param istanza
     * @return
     */
    public List<Istanzestradario> findByIstanza(Integer codiceIstanza);

    /**
     * Aggiorna il campo "valido" dell' oggetto IstanzeStradario
     * 
     * @param codiceIstanzaStradario
     * @param value
     */
    public void updateFieldValido(Integer codiceIstanzaStradario, Boolean value);

    /**
     * Aggiorna il campo "codiceCivico" dell' oggetto IstanzeStradario
     * 
     * @param codiceIstanzaStradario
     * @param value
     */
    public void updateFieldCodicecivico(Integer codiceIstanzaStradario, String codiceCivico);

    /**
     * 
     * @param codiceistanza
     * @return
     */
    public IstanzestradarioDTO findIstanzeStradarioDTOByIstanza(Integer codiceistanza);

    public void updateSettaANullNonValidi();

    public Istanzestradario findByUuid(Integer codiceIstanza, String uuid);

    public List<IstanzeStradarioExtendedDTO> findByIstanzeFilter(IstanzeFilter filter, Integer firstResult, Integer maxResults);

    public List<IstanzeStradarioExtendedDTO> findByTmp(String token);

    public IstanzeStradarioExtendedDTO findById(Integer idIstanzeStradario);

    public List<IstanzeStradarioExtendedDTO> findByAutorizzazioniFilter(DetachedCriteria autorizzazioniCriteria, Integer firstResult,
	    Integer maxResult);
}

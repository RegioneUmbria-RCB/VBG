/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface InventarioprocedimentiDAO extends BaseDAO<Inventarioprocedimenti, PkId> {

    /**
     * Lista di inventari procedimenti filtrati per idcomune e software e ordinata per procedimento
     * 
     */
    public List<Inventarioprocedimenti> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param tipiendo
     * @return ritorna una lista di inventarioprocedimenti a cui è associata la categoria di endo procedimenti scelta
     */
    public List<Inventarioprocedimenti> findByTipoendo(Tipiendo tipiendo);

    /**
     * Metodo per filtrare gli InventariProcedimeniti per il "like" della descrizione, il codice del tipofamigliaendo e
     * per il codice di tipoendo
     * 
     * @param textToSearch
     * @param codiceFamiglia
     * @param codiceTipologia
     * @param escludiDisabilitati
     * @return
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, String idComuneFamiglia,
	    Integer codiceTipologia, String idComuneTipologia, Boolean escludiDisabilitati);

    /**
     * torna la lista di tutti gli endo procedimenti che non hanno legami con la tabella Stp_Endo_tipo1
     * 
     * @return
     */
    public List<Inventarioprocedimenti> findAllNonStp();

    /**
     * Metodo per filtrare gli InventariProcedimeniti per il "like" della descrizione, il codice software
     * 
     * 
     * @return
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoAndSoftware(String descrizione, String codicesoftware);

    //----------------------------------------------------------------------------------------------------------------------------------///
    //------------------------------------------SEZIONE DEDICATA ALLA NUOVA GESTIONE JMESA----------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    /**
     * Ritorna un intero che rappresenta il numero di recor filtatarti per il parametro inventarioprocedimento
     * 
     * @param inventarioprocedimenti
     * @return
     */
    public int countRecordByFilter(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * <pre>
     * Ritorna una lista di inventario procedimenti filtrati per i campi di inventario procedimenti passati
     * @param inventarioprocedimenti 	: filtro
     * @param startRowPage		: record da		
     * @param endRowPage		: record a
     * @return
     * </pre>
     */
    public List<Inventarioprocedimenti> findByInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti, Integer startRowPage,
	    Integer endRowPage);

    //----------------------------------------------------------------------------------------------------------------------------------///
    //---------------------------------------------------------------END----------------------------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    public List<Integer> findCodiciEndoPerSoftware(String software);

    public List<Tipifamiglieendo> findTutteFamiglieEndoByCurrSoftwareAndTT(FlagPubblicaEnum flagPubblicaEnum);

    public List<Tipiendo> findTutteTipologieEndoByCurrSoftwareAndTT(Integer codiceFamiglia, FlagPubblicaEnum pubblicaEnum);
}

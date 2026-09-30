package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

public interface Dyn2ModellidService extends BaseService<Dyn2Modellid, PkId> {

    /**
     * Lista di dyn2CampiModelliD filtrati per Dyn2ModelloT
     * 
     * @param dyn2Modellit
     * @return
     */
    List<Dyn2Modellid> findByModelloT(Dyn2Modellit dyn2Modellit);

    /**
     * @see Dyn2ModellidDAO#findMaxRigaByModelloT(Dyn2Modellit dyn2Modellit)
     */
    public Integer findMaxRigaByModelloT(Dyn2Modellit dyn2Modellit);

    /**
     * @see Dyn2ModellidDAO#findMaXColonnaByRigaModelloDAndModelloT(Dyn2Modellit dyn2Modellit, Dyn2Modellid
     *      dyn2Modellid)
     */
    public Integer findMaXColonnaByRigaModelloDAndModelloT(Dyn2Modellit dyn2Modellit, Dyn2Modellid dyn2Modellid);

    /**
     * Il metodo deve aggiornare il flgMultiplo per il modello d scelto; inoltre deve controllare se ci sono per il
     * modello T a cui è associato il modello d scelto altri modelli d con la stessa riga ed eventualmente aggiornare
     * anche queste.
     * 
     * @param dyn2Modellid
     */
    public void updateFlagMultiplo(Dyn2Modellid dyn2Modellid);

    /**
     * Lista di dyn2CampiModelliD filtrati per Dyn2ModelloT e Riga (posverticale)
     * 
     * @param dyn2Modellit
     * @return
     */
    public List<Dyn2Modellid> findModelliDByModelliTAndRiga(Dyn2Modellit dyn2Modellit, Integer posverticale);

    /**
     * Recupera le righe di un modello ordinate per posverticale asc, posorizzontale asc
     * 
     * @param idModello
     * @return
     */
    public List<Dyn2Modellid> findRigheModello(String idComuneModello, int idModello);

    /**
     * Verifica l'esistenza del campo dinamico passato nella configurazione di tutti i modelli associati all'istanze
     * apparteneti all'attivita passata e con data validità uguale alla data dello snapshot di riferimento.
     * 
     * ( La funzionalità viene utilizzata per verificare se un campo dinamico è previsto dalla configurazione dei
     * modelli delle istanze che appartengono allo snapshot. Non possiamo controllare su istanzedyyn2dati perchè
     * eventuali campi non valorizzati non sono presenti in quella tabella )
     * 
     * @param codiceAttivita
     * @param dataSnapshot
     * @param codiceCampo
     * @return
     */
    boolean isExistDyn2CampiInDyn2ModelliDIstanzeAndAttivita(Integer codiceAttivita, Date dataSnapshot, Integer codiceCampo);

    /**
     * Ritorna una lista di modelli d a cui è associata la regola (Dyn2_regole) passata
     * 
     * @param codiceRegola
     * @return
     */
    public List<Dyn2Modellid> findByRegola(Integer codiceRegola);

    /**
     * Trova le istanze di {@link Dyn2Modellid} che rappresentano tutti i modelli in cui è utilizzato il campo passato
     * come argomento.
     * 
     * @param codiceCampo
     * @return
     */
    public List<Dyn2Modellid> findByCampo(Integer codiceCampo);

    /**
     * 
     * @param idcomune
     * @param codicemodello
     * @param codiceCampo
     * @return
     */
    public List<Dyn2Modellid> findByModelloAndCampo(String idcomune, Integer codicemodello, Integer codiceCampo, Integer firstResult,
	    Integer maxResult);

    /**
     * 
     * @param idcomune
     * @param codicemodello
     * @param codiceCampo
     * @return
     */
    public Boolean findObbligatorioByModelloAndCampo(String idcomune, Integer codicemodello, Integer codiceCampo);
}

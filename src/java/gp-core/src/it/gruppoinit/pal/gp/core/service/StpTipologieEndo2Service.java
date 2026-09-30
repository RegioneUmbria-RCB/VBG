package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;

public interface StpTipologieEndo2Service extends BaseService<StpTipologieEndo2, PkId> {

    /**
     * <pre>
     * Il metodo cicla l'array dei codici codiciTipologieEndo2 e controlla se per il codice i-esimo esiste gia una TipologiaEndoTipo2:
     * 
     *  <ol>
     *     <li>
     *     	Non Eiste : Crea un nuovo oggetto , setta l'azione se è passata ed inserisce l'oggetto, se l'azione non è passata setta la variabile 
     *     	boolean di controllo a false
     *     </li>
     *     <li>
     *     	 Eiste : Setta l'azione se è passata ed fa l'update dell'oggetto, se l'azione non è passata setta la variabile 
     *     	 boolean di controllo a false
     *     </li>
     *  </ol>   
     *  
     * @param codiceAzioni
     * @param codiciTipologieEndo2
     * @param descrizioneTipologieEndo2
     * @return true : configurazione completata,false : configurazione non completata.
     * </pre>
     */
    public boolean updateStpTipologieEndo2AndValidateConfiguration(String[] codiceAzioni, String[] codiciTipologieEndo2,
	    String[] descrizioneTipologieEndo2);
}

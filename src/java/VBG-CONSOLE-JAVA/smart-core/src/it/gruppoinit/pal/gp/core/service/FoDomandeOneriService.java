/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;

/**
 * @author francol
 *
 */
public interface FoDomandeOneriService extends BaseService<FoDomandeOneri, PkId> {

    public static enum StatiPagamentoEnum {
	Effettuato("Effettuato"), Online("Online");

	private String value;

	private StatiPagamentoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    };

    /**
     * Restituisce tutti i record degli oneri associati alla domanda FO passata come argomento
     * 
     * @param idDomanda
     * @return
     */
    public List<FoDomandeOneri> findOneriByDomanda(Integer idDomanda, String idComuneDomanda);

    /**
     * Restituisce tutti i record degli oneri associati alla domanda FO passata come argomento
     * 
     * @param idDomanda
     * @return
     */
    public List<FoDomandeOneri> findOneriByDomanda(FoDomande domanda);

    /**
     * Restituisce una lista di FoDomandeOneri per ciascun onere per cui sia previsto il pagamento in base alla lista di
     * endoprocedimenti i cui id sono passati come argomento. I record restituiti possono essere già presenti nel db
     * oppure sono oggetti creati con new FoDomandeOneri per tutti gli oneri previsti ma che non sono ancoa mai stati
     * salvati per la domanda
     * 
     * @return
     */
    public List<FoDomandeOneri> loadOneriForProcedimenti(FoDomande domanda, String codiceComuneGruppo, List<PkId> idProcedimenti);

    /**
     * Cancella la ricevuta onere il cui codice oggetto e id comune sono passati come argomenti. Prima della
     * cancellazione fisica da Oggetti l'oggetto viene eliminato dagli allegati della domanda e disassociato dal record
     * di fo_domande_oneri
     * 
     * @param codiceOggetto
     * @param idComuneOggetto
     */
    public void deleteRicevutaOnere(Integer codiceOggetto, String idComuneOggetto);

    /**
     * restituisce i record di FoDomandeOneri che fanno riferimento alla ricevuta la cui pk è passata come argomento,
     * restituisce una lista ma in base a come viene popolato il DB si presume che contenga un solo record.
     * 
     * @param codOggettoRicevuta
     * @param idComune
     * @return
     */
    public List<FoDomandeOneri> findOneriByRicevuta(Integer codOggettoRicevuta, String idComune);

    /**
     * Salva nel db i dati degli oneri passati come primo argomento associandoli alla domanda passata come secondo
     * argomento restituisce la lista degli stessi oneri passati in input ma come oggetti popolati dal DB anzichè
     * popolati dai campi della UI, questo è necessario per avere negli oggetti della lista i riferimenti a
     * {@link Inventarioprocedimentioneri} popolati correttamente da Hibernate
     * 
     * @param oneri
     */
    public List<FoDomandeOneri> salvaDatiOneri(List<FoDomandeOneri> oneri, Integer idDomanda);

    /**
     * Metodo non transazionale che contiene la logica di validazione degli oneri della domanda
     */
    public List<ErroreValidazione> validazioneOneri(List<FoDomandeOneri> oneri);

    /**
     * Restituisce l'elenco dei possibili valori per il campo stato pagamento
     * 
     * @return
     */
    public List<String> getListaStatiPagamento();
}

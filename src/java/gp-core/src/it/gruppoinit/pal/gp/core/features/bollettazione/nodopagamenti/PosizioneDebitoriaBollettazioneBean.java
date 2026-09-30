package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.PosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.RataBean;

public class PosizioneDebitoriaBollettazioneBean extends PosizioneDebitoriaBean {

    private List<Integer> idRigheBollettazione;

    private PosizioneDebitoriaBollettazioneBean(String codiceFiscaleEnteCreditore, List<RataBean> rate, Anagrafe soggettoDebitore, Date data,
	    String causale, Integer idDettaglioPosizioneDebitoria, String codiceComune,
	    String descrizioneRegistrazioneContabile, List<Integer> idRigheBollettazione) {

	super(codiceFiscaleEnteCreditore, rate, soggettoDebitore, data, causale, idDettaglioPosizioneDebitoria, codiceComune,
		descrizioneRegistrazioneContabile);
	this.idRigheBollettazione = idRigheBollettazione;
    }

    public PosizioneDebitoriaBollettazioneBean(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore, List<ImportoBean> importi, Date data,
	    String descrizione, List<Integer> idRigheBollettazione, String causale, Date dataScadenza, Integer idDettPosizioneDebitoria,
	    int numerorata, String codiceComune) {

	super(codiceFiscaleEnteCreditore, soggettoDebitore, importi, data, descrizione, causale, dataScadenza, idDettPosizioneDebitoria, numerorata,
		null/* nella bollettazione non mando il riferimento client */ , codiceComune);
	this.idRigheBollettazione = idRigheBollettazione;
    }

    public List<Integer> getIdRigheBollettazione() {

	return idRigheBollettazione;
    }

    public static PosizioneDebitoriaBollettazioneBean fromImportoSingolo(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore,
	    ImportoBean importo, Date data, String descrizione, List<Integer> idRigheBollettazione, String causale, Date dataScadenza,
	    Integer idDettPosizioneDebitoria, int numerorata, String codiceComune) {

	List<ImportoBean> importi = new ArrayList<ImportoBean>();
	importi.add(importo);
	return new PosizioneDebitoriaBollettazioneBean(codiceFiscaleEnteCreditore, soggettoDebitore, importi, data, descrizione, idRigheBollettazione,
		causale, dataScadenza, idDettPosizioneDebitoria, numerorata, codiceComune);
    }

    public static PosizioneDebitoriaBollettazioneBean conRate(String codiceFiscaleEnteCreditore, List<RataBean> rate, Anagrafe soggettoDebitore,
	    Date data, String causale, Integer idDettPosizioneDebitoria, String codiceComune, String descrizioneRegistrazioneContabile,
	    List<Integer> idRigheBollettazione) {

	return new PosizioneDebitoriaBollettazioneBean(codiceFiscaleEnteCreditore, rate, soggettoDebitore, data, causale, idDettPosizioneDebitoria,
		codiceComune, descrizioneRegistrazioneContabile, idRigheBollettazione);
    }
}

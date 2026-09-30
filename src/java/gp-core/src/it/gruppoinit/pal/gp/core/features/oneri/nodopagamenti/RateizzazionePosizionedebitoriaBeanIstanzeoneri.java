package it.gruppoinit.pal.gp.core.features.oneri.nodopagamenti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.paevolution.ws.pagamenti_types.ImportoPagamentoWsInType;
import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.PosizioneDebitoriaWsInType;
import com.paevolution.ws.pagamenti_types.RegistrazioneContabileWsInType;

/**
 * Questa claswe funziona solamente per oneri con la stessa causale
 * 
 * @author riccardob
 *
 */
public class RateizzazionePosizionedebitoriaBeanIstanzeoneri {

    private List<PosizioneDebitoriaBeanIstanzeoneri> rate;

    public String getCodiceComune() {

	return this.rate.get(0).getCodiceComune();
    }

    public RateizzazionePosizionedebitoriaBeanIstanzeoneri(List<PosizioneDebitoriaBeanIstanzeoneri> rate) {

	super();
	if (rate == null || rate.isEmpty()) {
	    throw new IllegalArgumentException("Nessuna posizione debitoria passata");
	}
	this.rate = rate;
    }

    /**
     * Nella rateizzata ho una serie di PosizioneDebitoriaBeanIstanzeoneri. la testata la devo usare per tutte le altre
     * rate che i dati sono uguali. Estraggo la testata e faccio un nuovo oggetto con le rate estratte e l'importo
     * calcolato.
     * 
     * @return
     */
    public InserisciPosizioniDebitorieType toInserisciPosizioneDebitorieType() {

	InserisciPosizioniDebitorieType ret = new InserisciPosizioniDebitorieType();
	ret.setAccorpaPosizioni(false);
	boolean testataOK = false;
	List<PosizioneDebitoriaWsInType> ratei = new ArrayList<PosizioneDebitoriaWsInType>();
	for (PosizioneDebitoriaBeanIstanzeoneri posizioneDebitoriaBeanIstanzeoneri : rate) {
	    //
	    InserisciPosizioniDebitorieType ipd = posizioneDebitoriaBeanIstanzeoneri.toInserisciPosizioneDebitoriaType();
	    ret.setCfEnteCreditore(ipd.getCfEnteCreditore());
	    // converto l'oggetto nel tipo ed estraggo la testata 
	    RegistrazioneContabileWsInType rc = ipd.getRegistrazione().get(0);
	    if (!testataOK) {
		ret.getRegistrazione().add(0, rc); // ***
		testataOK = true;
	    }
	    // la testata sta sempre nell'elemento 0 come da riga ***
	    ratei.addAll(rc.getRate());
	}
	ret.getRegistrazione().get(0).getRate().clear();
	ret.getRegistrazione().get(0).getRate().addAll(ratei);
	return ret;
    }

    public BigDecimal getImportoTotale() {

	BigDecimal importo = BigDecimal.ZERO;
	for (PosizioneDebitoriaBeanIstanzeoneri posizioneDebitoriaBeanIstanzeoneri : rate) {
	    //
	    InserisciPosizioniDebitorieType rata = posizioneDebitoriaBeanIstanzeoneri.toInserisciPosizioneDebitoriaType();
	    // converto l'oggetto nel tipo ed estraggo la testata 
	    List<RegistrazioneContabileWsInType> registrazioni = rata.getRegistrazione();
	    for (RegistrazioneContabileWsInType registrazioneContabileWsInType : registrazioni) {
		importo = setImporto(importo, registrazioneContabileWsInType);
	    }
	    //importo = importo.add(rc.getImporto());
	}
	return importo;
    }

    private BigDecimal setImporto(BigDecimal importo, RegistrazioneContabileWsInType registrazioneContabileWsInType) {

	List<PosizioneDebitoriaWsInType> rate = registrazioneContabileWsInType.getRate();
	for (PosizioneDebitoriaWsInType pd : rate) {
	    List<ImportoPagamentoWsInType> importi = pd.getImporti();
	    for (ImportoPagamentoWsInType importoPagamentoWsInType : importi) {
		importo = importoPagamentoWsInType.getImporto();
	    }
	}
	return importo;
    }

    public BigDecimal getImportoPosizioneDebitoria(List<String> codiceriferimentoistanzeoneri) {

	// converto l'oggetto nel tipo ed estraggo la testata 
	for (PosizioneDebitoriaBeanIstanzeoneri pdio2 : rate) {
	    for (String rif : pdio2.getRiferimentoClient()) {
		for (String rifonere : codiceriferimentoistanzeoneri) {
		    if (rifonere.equalsIgnoreCase(rif)) {
			return pdio2.getImportoTotale();
		    }
		}
	    }
	}
	throw new IllegalArgumentException("Importo non trovato nella posizione debitore di istanzeoneri con id " + codiceriferimentoistanzeoneri);
    }

    public PosizioneDebitoriaBeanIstanzeoneri getPosizioneDebitoriaBeanIstanzeOneri(List<String> codiceriferimentoistanzeoneri) {

	for (PosizioneDebitoriaBeanIstanzeoneri pdio2 : rate) {
	    for (String rif : pdio2.getRiferimentoClient()) {
		for (String rifonere : codiceriferimentoistanzeoneri) {
		    if (rifonere.equalsIgnoreCase(rif)) {
			return pdio2;
		    }
		}
	    }
	}
	throw new IllegalArgumentException("Posizione debitoria non trovata per istanzeoneri con id " + codiceriferimentoistanzeoneri);
    }
}

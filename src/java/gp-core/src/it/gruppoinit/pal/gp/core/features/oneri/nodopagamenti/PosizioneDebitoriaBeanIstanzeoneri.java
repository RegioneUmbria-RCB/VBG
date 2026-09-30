package it.gruppoinit.pal.gp.core.features.oneri.nodopagamenti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.PosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.service.TipicausalioneridettaglioService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class PosizioneDebitoriaBeanIstanzeoneri extends PosizioneDebitoriaBean {

    private Integer codiceAnagrafe;

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public static PosizioneDebitoriaBeanIstanzeoneri newInstance(String arCodfiscaleEnteCreditore, Set<Istanzeoneri> istanzeoneri,
	    TipicausalioneridettaglioService serviceDettagliCausali, Anagrafe richiedente) {

	//1. validazione ( controllare che il set istanzeoneri sia congruente con una sola istanza e con un solo codiceCausalePeople passato )
	Istanzeoneri primaOccorrenza = istanzeoneri.iterator().next();
	List<String> rifClient = new ArrayList<String>();
	//2. Recupero degli importi
	List<ImportoBean> importi = new ArrayList<ImportoBean>();
	for (Istanzeoneri onere : istanzeoneri) {
	    rifClient.add(String.valueOf(onere.getId().getCodice()));
	    Tipicausalioneri tco = onere.getTipicausalioneri();
	    Conti conto = serviceDettagliCausali.findContoAttivoByCausaleOneri(tco.getId().getCodice());
	    ImportoBean ib = new ImportoBean(onere.getPrezzo(), conto.getMappaturanodopag());
	    importi.add(ib);
	}
	//3. Calcolo della descrizione
	String descrizione = buildDescrizione(primaOccorrenza.getIstanza(), primaOccorrenza.getTipicausalioneri());
	String codiceComune = primaOccorrenza.getIstanza().getComune().getCodicecomune();
	//4. Causale
	String causale = primaOccorrenza.getTipicausalioneri().getCoDescrizione();
	return new PosizioneDebitoriaBeanIstanzeoneri(arCodfiscaleEnteCreditore, richiedente, importi, primaOccorrenza.getData(), descrizione,
		causale, primaOccorrenza.getDatascadenza(), null, primaOccorrenza.getNumerorata(), rifClient, codiceComune);
    }

    public static PosizioneDebitoriaBeanIstanzeoneri newInstance(Istanzeoneri istanzeoneri, String arCodfiscaleEnteCreditore, Conti conto,
	    Anagrafe richiedente) {

	validateInput(istanzeoneri, arCodfiscaleEnteCreditore, conto);
	List<ImportoBean> importo = new ArrayList<ImportoBean>();
	Tipicausalioneri tco = istanzeoneri.getTipicausalioneri();
	ImportoBean ib = new ImportoBean(istanzeoneri.getPrezzo(), conto.getMappaturanodopag());
	importo.add(ib);
	String descrizione = buildDescrizione(istanzeoneri.getIstanza(), tco);
	String causale = tco.getCoDescrizione();
	String codiceComune = istanzeoneri.getIstanza().getComune().getCodicecomune();
	List<String> rifClient = new ArrayList<String>();
	rifClient.add(String.valueOf(istanzeoneri.getId().getCodice()));
	return new PosizioneDebitoriaBeanIstanzeoneri(arCodfiscaleEnteCreditore, richiedente, importo, istanzeoneri.getData(), descrizione, causale,
		istanzeoneri.getDatascadenza(), null, istanzeoneri.getNumerorata(), rifClient, codiceComune);
    }

    private List<String> riferimentoClient = null;

    private PosizioneDebitoriaBeanIstanzeoneri(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore, List<ImportoBean> importi, Date data,
	    String descrizione, String causale, Date dataScadenza, Integer idDettaglioPosizioneDebitoria, int numerorata,
	    List<String> riferimentoClient, String codiceComune) {

	super(codiceFiscaleEnteCreditore, soggettoDebitore, importi, data, descrizione, causale, dataScadenza, idDettaglioPosizioneDebitoria,
		numerorata, riferimentoClient, codiceComune);
	this.riferimentoClient = riferimentoClient;
	if (soggettoDebitore.getId() != null) {
	    this.codiceAnagrafe = soggettoDebitore.getId().getCodice();
	}
    }

    public static void validateInput(Istanzeoneri istanzeoneri, String arCodfiscaleEnteCreditore, Conti conto) {

	if (istanzeoneri == null || istanzeoneri.getTipicausalioneri() == null || conto == null) {
	    String errore = String.format("Parametri non validi: istanzeoneri %s, arCodfiscaleEnteCreditore %s, conto %s", istanzeoneri,
		    arCodfiscaleEnteCreditore, conto);
	    throw new IllegalArgumentException(errore);
	}
	if (istanzeoneri.getPrezzo() == null || istanzeoneri.getPrezzo().compareTo(BigDecimal.ZERO) <= 0) {
	    throw new IllegalArgumentException("Parametri non validi importo " + istanzeoneri.getPrezzo());
	}
	if (istanzeoneri.getImportopagato() != null && istanzeoneri.getImportopagato().compareTo(BigDecimal.ZERO) > 0) {
	    String errore = String.format("Parametri non validi: per l'onere con id %s risulta già pagato un importo %s", istanzeoneri.getId(),
		    istanzeoneri.getImportopagato());
	    throw new IllegalArgumentException(errore);
	}
	if (!BooleanUtils.isTrue(istanzeoneri.getFlentratauscita())) {
	    String errore = String.format("Parametri non validi: l'onere con id %s risulta in ingresso e non in uscita %s", istanzeoneri.getId(),
		    istanzeoneri.getImportopagato());
	    throw new IllegalArgumentException(errore);
	}
	if (istanzeoneri.getIstoneriDettPosizioni() != null && !istanzeoneri.getIstoneriDettPosizioni().isEmpty()) {
	    String format = String.format("Parametri non validi: l'onere con id %s risulta già associato ad una posizione debitoria ",
		    istanzeoneri.getId());
	    throw new IllegalArgumentException(format);
	}
    }

    private static String buildDescrizione(Istanze istanza, Tipicausalioneri tco) {

	String descPratica = istanza.getNumeroistanza();
	if (StringUtils.isNotBlank(istanza.getNumeroprotocollo())) {
	    descPratica += " (prot. " + istanza.getNumeroprotocollo();
	    if (istanza.getDataprotocollo() != null) {
		descPratica += " del " + Utilities.formatDate(istanza.getDataprotocollo(), false);
	    }
	    descPratica += ")";
	}
	String prefisso = tco.getCoDescrizione();
	if (tco.getRaggruppamentocausalioneri() != null && StringUtils.isNotBlank(tco.getRaggruppamentocausalioneri().getRcoDescr())) {
	    prefisso = tco.getRaggruppamentocausalioneri().getRcoDescr();
	}
	return prefisso + " della pratica num. " + descPratica;
    }

    public List<String> getRiferimentoClient() {

	return riferimentoClient;
    }
}

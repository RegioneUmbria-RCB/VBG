package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.PosizioneDebitoriaWsInType;
import com.paevolution.ws.pagamenti_types.RegistrazioneContabileWsInType;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class PosizioneDebitoriaBean {

    private String codiceFiscaleEnteCreditore;
    private SoggettoDebitoreDaAnagrafe soggettoDebitore;
    private Date data;
    private String causale;
    private Integer idDettaglioPosizioneDebitoria;
    private String codiceComune;
    private List<RataBean> rate;
    private String descrizioneRegistrazioneContabile;

    public String getCodiceComune() {

	return codiceComune;
    }

    public PosizioneDebitoriaBean(String codiceFiscaleEnteCreditore, List<RataBean> rate, Anagrafe soggettoDebitore, Date data, String causale,
	    Integer idDettaglioPosizioneDebitoria, String codiceComune, String descrizioneRegistrazioneContabile) {

	super();
	this.rate = new ArrayList<RataBean>();
	if (rate != null) {
	    this.rate.addAll(rate);
	}
	this.codiceFiscaleEnteCreditore = codiceFiscaleEnteCreditore;
	this.soggettoDebitore = new SoggettoDebitoreDaAnagrafe(soggettoDebitore);
	this.data = data;
	this.causale = causale;
	this.idDettaglioPosizioneDebitoria = idDettaglioPosizioneDebitoria;
	this.codiceComune = codiceComune;
	this.descrizioneRegistrazioneContabile = descrizioneRegistrazioneContabile;
    }

    public PosizioneDebitoriaBean(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore, List<ImportoBean> importi, Date data,
	    String descrizione, String causale, Date dataScadenza, Integer idDettaglioPosizioneDebitoria, int numerorata,
	    List<String>riferimentoClientOnereDaInserire, String codiceComune) {

	super();
	this.rate = new ArrayList<RataBean>();
	this.rate.add(new RataBean(riferimentoClientOnereDaInserire, importi, descrizione, numerorata, dataScadenza));
	this.codiceFiscaleEnteCreditore = codiceFiscaleEnteCreditore;
	this.soggettoDebitore = new SoggettoDebitoreDaAnagrafe(soggettoDebitore);
	this.data = data;
	this.causale = causale;
	this.idDettaglioPosizioneDebitoria = idDettaglioPosizioneDebitoria;
	this.codiceComune = codiceComune;
	this.descrizioneRegistrazioneContabile = descrizione;
    }

    public String getCodiceFiscaleEnteCreditore() {

	return codiceFiscaleEnteCreditore;
    }

    public SoggettoDebitoreDaAnagrafe getSoggettoDebitore() {

	return soggettoDebitore;
    }

    public Date getData() {

	return data;
    }

    public String getCausale() {

	return causale;
    }

    public InserisciPosizioniDebitorieType toInserisciPosizioneDebitoriaType() {

	InserisciPosizioniDebitorieType pos = new InserisciPosizioniDebitorieType();
	pos.setAccorpaPosizioni(false);
	pos.setCfEnteCreditore(this.codiceFiscaleEnteCreditore);
	RegistrazioneContabileWsInType r = new RegistrazioneContabileWsInType();
	if (this.data != null) {
	    r.setData(Utilities.getXMLGregorianCalendar(this.data));
	}
	r.setDescrizione(StringUtils.defaultIfEmpty(descrizioneRegistrazioneContabile, causale));
	Calendar c = Calendar.getInstance();
	c.setTime(this.data);
	r.setAnno(c.get(Calendar.YEAR));
	pos.getRegistrazione().add(r);
	r.setSoggettoDebitore(this.soggettoDebitore.toSoggettoDebitoreType());
	for (RataBean rata : this.rate) {
	    PosizioneDebitoriaWsInType pd = new PosizioneDebitoriaWsInType();
	    pd.getRiferimentiClient().addAll(rata.getRiferimentoClientOnereDaInserire());
	    pd.setDescrizione(rata.getDescrizione());
	    pd.setNumeroRata(BigInteger.valueOf(rata.getNumerorata()));
	    if (rata.getDataScadenza() != null) {
		pd.setDataScadenza(Utilities.getXMLGregorianCalendar(rata.getDataScadenza()));
	    }
	    Map<String, ImportoBean> listaImportiMappati = new HashMap<String, ImportoBean>();
	    for (ImportoBean importoBean : rata.getImporti()) {
		ImportoBean ib = listaImportiMappati.get(importoBean.getMappaturaNodoPag());
		if (ib == null) {
		    ib = ImportoBean.copy(importoBean);
		} else {
		    ib.addImporto(importoBean.getImporto());
		}
		listaImportiMappati.put(importoBean.getMappaturaNodoPag(), ib);
	    }
	    for (Entry<String, ImportoBean> rataBean : listaImportiMappati.entrySet()) {
		pd.getImporti().add(rataBean.getValue().toImportoPagamentoType());
	    }
	    r.getRate().add(pd);
	}
	return pos;
    }

    public BigDecimal getImportoTotale() {

	BigDecimal impTotale = BigDecimal.ZERO;
	for (RataBean rata : rate) {
	    for (ImportoBean importoBean : rata.getImporti()) {
		impTotale = impTotale.add(importoBean.getImporto());
	    }
	}
	return impTotale;
    }

    public Integer getIdDettaglioPosizioneDebitoria() {

	return idDettaglioPosizioneDebitoria;
    }

    public String getDescrizioneRegistrazioneContabile() {

	return descrizioneRegistrazioneContabile;
    }

    public List<RataBean> getRate() {

	return rate;
    }
}

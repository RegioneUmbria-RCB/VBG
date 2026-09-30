package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author gianpaolot Bean utilizzato per la funzionalità pagamento utenze posteggi
 * 
 */
public class PagamentoUtenze {

    private Date datascadenza;
    private Date dataregistrazione;
    private Mercati mercati;
    private MercatiUso mercatiUso;
    private RegistrazioniCausali registrazioniCausali;
    private Conti conti;
    private BigDecimal importi;
    private List<RegistrazioniImporti> registrazioneImportiList;
    private List<Posteggio> posteggiList;
    // viene settato se si viene dal metodo nel cancellazione
    private Integer flagCancellato;
    // flag utilizzate per saperer se si considera o no la rateizzazione
    private Boolean rate;

    public PagamentoUtenze() {

	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
	this.registrazioniCausali = new RegistrazioniCausali();
	this.flagCancellato = 0;
	this.conti = new Conti();
	this.importi = new BigDecimal(0.00);
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public void setRegistrazioniCausali(RegistrazioniCausali registrazioniCausali) {

	this.registrazioniCausali = registrazioniCausali;
    }

    public RegistrazioniCausali getRegistrazioniCausali() {

	return registrazioniCausali;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }

    public Conti getConti() {

	return conti;
    }

    public void setImporti(BigDecimal importi) {

	this.importi = importi;
    }

    public BigDecimal getImporti() {

	return importi;
    }

    public List<RegistrazioniImporti> getRegistrazioneImportiList() {

	return registrazioneImportiList;
    }

    public void setRegistrazioneImportiList(List<RegistrazioniImporti> registrazioneImportiList) {

	this.registrazioneImportiList = registrazioneImportiList;
    }

    public List<Posteggio> getPosteggiList() {

	return posteggiList;
    }

    public void setPosteggiList(List<Posteggio> posteggiList) {

	this.posteggiList = posteggiList;
    }

    public void setFlagCancellato(Integer flagCancellato) {

	this.flagCancellato = flagCancellato;
    }

    public Integer getFlagCancellato() {

	return flagCancellato;
    }

    public void setRate(Boolean rate) {

	this.rate = rate;
    }

    public Boolean getRate() {

	return rate;
    }
}

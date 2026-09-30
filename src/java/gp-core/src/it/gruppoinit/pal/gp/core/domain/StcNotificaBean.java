package it.gruppoinit.pal.gp.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.DocumentiistanzaValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2ModellitValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeallegatiValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MovimentiallegatiValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.TipimovStcAltridatiValoreBean;

public class StcNotificaBean {

    protected String numeroPratica;
    protected Date dataPratica;
    protected String numeroProtocolloGenerale;
    protected Date dataProtocolloGenerale;
    private Movimenti movimento;
    private Tipimovimento tipimovimento;
    private List<TipimovStcAltridatiValoreBean> altriDatiList = new ArrayList<TipimovStcAltridatiValoreBean>();
    private List<IstanzeallegatiValoreBean> istanzeAllegatiList = new ArrayList<IstanzeallegatiValoreBean>();
    private List<DocumentiistanzaValoreBean> documentiistanzaList = new ArrayList<DocumentiistanzaValoreBean>();
    private List<MovimentiallegatiValoreBean> movimentiallegatiList = new ArrayList<MovimentiallegatiValoreBean>();
    private DocumentiHelper documentiHelper;
    private List<Dyn2ModellitValoreBean> modelliList = new ArrayList<Dyn2ModellitValoreBean>();

    public StcNotificaBean() {

	this.movimento = new Movimenti();
    }

    public Tipimovimento getTipimovimento() {

	if (this.movimento != null && this.movimento.getTipomovimento() != null) {
	    this.tipimovimento = this.movimento.getTipomovimento();
	}
	return this.tipimovimento;
    }

    public Amministrazioni getAmministrazioniStc() {

	if (this.movimento != null && this.movimento.getAmministrazioniStc() != null) {
	    return this.movimento.getAmministrazioniStc();
	}
	return null;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    public Movimenti getMovimento() {

	return movimento;
    }

    public boolean isNotificainterapratica() {

	if (this.movimento != null && this.movimento.getTipomovimento() != null) {
	    Amministrazioni amministrazioni = this.getAmministrazioniStc();
	    Tipimovimento tipoMovimento = this.movimento.getTipomovimento();
	    Set<TipimovStcMapping> mappings = tipoMovimento.getTipimovStcMappings();
	    for (TipimovStcMapping tipimovStcMapping : mappings) {
		Amministrazioni amministrazioni2 = tipimovStcMapping.getAmministrazioni();
		if (amministrazioni.getId().equals(amministrazioni2.getId())) {
		    return BooleanUtils.isTrue(tipimovStcMapping.getFlagNotificainterapratica());
		}
	    }
	}
	return false;
    }

    public boolean isPraticaStorica() {

	if (this.movimento != null && this.movimento.getTipomovimento() != null) {
	    Amministrazioni amministrazioni = this.getAmministrazioniStc();
	    Tipimovimento tipoMovimento = this.movimento.getTipomovimento();
	    Set<TipimovStcMapping> mappings = tipoMovimento.getTipimovStcMappings();
	    for (TipimovStcMapping tipimovStcMapping : mappings) {
		Amministrazioni amministrazioni2 = tipimovStcMapping.getAmministrazioni();
		if (amministrazioni.getId().equals(amministrazioni2.getId())) {
		    return (tipimovStcMapping.getFlagRifpratStorica() == null || tipimovStcMapping.getFlagRifpratStorica().equals(false))
			    ? Boolean.FALSE
			    : Boolean.TRUE;
		}
	    }
	}
	return false;
    }

    public boolean isNonInviareProcedimenti() {

	if (this.movimento != null && this.movimento.getTipomovimento() != null) {
	    Amministrazioni amministrazioni = this.getAmministrazioniStc();
	    Tipimovimento tipoMovimento = this.movimento.getTipomovimento();
	    Set<TipimovStcMapping> mappings = tipoMovimento.getTipimovStcMappings();
	    for (TipimovStcMapping tipimovStcMapping : mappings) {
		Amministrazioni amministrazioni2 = tipimovStcMapping.getAmministrazioni();
		if (amministrazioni.getId().equals(amministrazioni2.getId())) {
		    return (tipimovStcMapping.getNonInviareProcedimenti() == null || tipimovStcMapping.getNonInviareProcedimenti().equals(false))
			    ? Boolean.FALSE
			    : Boolean.TRUE;
		}
	    }
	}
	return false;
    }

    public void setAltriDatiList(List<TipimovStcAltridatiValoreBean> altriDatiList) {

	this.altriDatiList = altriDatiList;
    }

    public List<TipimovStcAltridatiValoreBean> getAltriDatiList() {

	if (altriDatiList.isEmpty() && this.getTipimovimento() != null) {
	    if (this.getAmministrazioniStc() != null) {
		Integer codiceAmministrazione = this.getAmministrazioniStc().getId().getCodice();
		int index = 0;
		for (TipimovStcAltridati tipimovStcAltridati : tipimovimento.getTipimovStcAltridatis()) {
		    if (codiceAmministrazione.intValue() == tipimovStcAltridati.getAmministrazioni().getId().getCodice().intValue()) {
			TipimovStcAltridatiValoreBean bean = new TipimovStcAltridatiValoreBean();
			bean.setChiave(tipimovStcAltridati);
			if (!(tipimovStcAltridati.getValoreDefaultCampo() == null || tipimovStcAltridati.getValoreDefaultCampo().equals(""))) {
			    bean.setValore(tipimovStcAltridati.getValoreDefaultCampo());
			}
			altriDatiList.add(index, bean);
			index++;
		    }
		}
	    }
	}
	return altriDatiList;
    }

    public void setModelliList(List<Dyn2ModellitValoreBean> modelliList) {

	this.modelliList = modelliList;
    }

    public List<Dyn2ModellitValoreBean> getModelliList() {

	return modelliList;
    }

    public void setNumeroPratica(String numeroPratica) {

	this.numeroPratica = numeroPratica;
    }

    public String getNumeroPratica() {

	return numeroPratica;
    }

    public void setNumeroProtocolloGenerale(String numeroProtocolloGenerale) {

	this.numeroProtocolloGenerale = numeroProtocolloGenerale;
    }

    public String getNumeroProtocolloGenerale() {

	return numeroProtocolloGenerale;
    }

    public List<IstanzeallegatiValoreBean> getIstanzeAllegatiList() {

	return istanzeAllegatiList;
    }

    public void setIstanzeAllegatiList(List<IstanzeallegatiValoreBean> istanzeAllegatiList) {

	this.istanzeAllegatiList = istanzeAllegatiList;
    }

    public List<DocumentiistanzaValoreBean> getDocumentiistanzaList() {

	return documentiistanzaList;
    }

    public void setDocumentiistanzaList(List<DocumentiistanzaValoreBean> documentiistanzaList) {

	this.documentiistanzaList = documentiistanzaList;
    }

    public List<MovimentiallegatiValoreBean> getMovimentiallegatiList() {

	return movimentiallegatiList;
    }

    public void setMovimentiallegatiList(List<MovimentiallegatiValoreBean> movimentiallegatiList) {

	this.movimentiallegatiList = movimentiallegatiList;
    }

    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    public Date getDataPratica() {

	return dataPratica;
    }

    public void setDataPratica(Date dataPratica) {

	this.dataPratica = dataPratica;
    }

    public Date getDataProtocolloGenerale() {

	return dataProtocolloGenerale;
    }

    public void setDataProtocolloGenerale(Date dataProtocolloGenerale) {

	this.dataProtocolloGenerale = dataProtocolloGenerale;
    }

    public void aggiungiAltroDato(String chiave, String valore) {

	TipimovStcAltridatiValoreBean bean = new TipimovStcAltridatiValoreBean();
	TipimovStcAltridati tipimovStcAltridati = new TipimovStcAltridati();
	tipimovStcAltridati.setFlagHelp(Boolean.FALSE);
	tipimovStcAltridati.setNomeCampo(chiave);
	tipimovStcAltridati.setValoreDefaultCampo(valore);
	bean.setChiave(tipimovStcAltridati);
	bean.setValore(valore);
	this.altriDatiList.add(bean);
    }
}

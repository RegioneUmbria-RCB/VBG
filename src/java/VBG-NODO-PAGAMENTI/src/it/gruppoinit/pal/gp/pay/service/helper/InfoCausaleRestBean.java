package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.parameters.DBParameter;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;

@XmlRootElement(name = "causale")
public class InfoCausaleRestBean {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "codice_versamento")
    private String codiceVersamento;
    @XmlElement(name = "mappatura_client")
    private String mappaturaClient;
    @XmlElement(name = "parametri")
    private Set<InfoParameterBean> params;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    public Set<InfoParameterBean> getParams() {

	if (this.params == null) {
	    this.params = new HashSet<>();
	}
	return params;
    }

    public void setParams(Set<InfoParameterBean> params) {

	this.params = params;
    }

    public String getMappaturaClient() {

	return mappaturaClient;
    }

    public void setMappaturaClient(String mappaturaClient) {

	this.mappaturaClient = mappaturaClient;
    }

    public static InfoCausaleRestBean fromPayRegistrazioniCausali(PayRegistrazioniCausali payRc, String javaClass) {

	InfoCausaleRestBean ret = new InfoCausaleRestBean();
	ret.setId(payRc.getId().getCodice());
	ret.setDescrizione(payRc.getDescrizione());
	ret.setCodiceVersamento(payRc.getCodiceVersamento());
	ret.setMappaturaClient(payRc.getMappaturaClient());
	Set<PayRegcausaliParametri> regParams = payRc.getRegParams();
	for (PayRegcausaliParametri payReg : regParams) {
	    DBParameter db = DBParameter.fromPayRegcausaliParametri(payReg);
	    List<IParameter> parametriConnettore = ParametriConnettoreHelper.getMappaParametriConnettore(javaClass);
	    String descrizione = db.getNomeParametro();
	    String help = "";
	    for (IParameter iParameter : parametriConnettore) {
		if (iParameter.getNomeParametro().equalsIgnoreCase(db.getNomeParametro())) {
		    descrizione = iParameter.getDescrizione();
		    help = iParameter.getHelp();
		    break;
		}
	    }
	    ret.getParams().add(new InfoParameterBean(db.getNomeParametro(), db.getValore(), descrizione, help));
	}
	return ret;
    }
}

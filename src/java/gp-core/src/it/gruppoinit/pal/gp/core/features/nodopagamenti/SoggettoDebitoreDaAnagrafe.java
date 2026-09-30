package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.apache.commons.lang.StringUtils;

import com.paevolution.ws.pagamenti_types.SoggettoDebitoreType;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;

public class SoggettoDebitoreDaAnagrafe {

    private String nome;
    private String cognome;
    private String cfpi;
    private String cap;
    private String via;
    private String civico;
    private String localita;
    private String provincia;
    private String stato;
    private String email;
    private Integer codiceAnagrafe;

    public SoggettoDebitoreDaAnagrafe(Anagrafe anagrafe) {

	super();
	if (anagrafe == null) {
	    throw new IllegalArgumentException("Non è possibile istanziare SoggettoDebitoreDaAnagrafe senza passare un'anagrafica");
	}
	if (StringUtils.isEmpty(anagrafe.getCodicefiscale()) && StringUtils.isEmpty(anagrafe.getPartitaiva())) {
	    throw new IllegalArgumentException(
		    "Non è possibile istanziare SoggettoDebitoreDaAnagrafe senza passare un'anagrafica con CF o PIVA valorizzati. Anagrafica passata " +
					       anagrafe.getId().getCodice());
	}
	this.email = anagrafe.getEmail();
	if (StringUtils.isBlank(StringUtils.defaultString(anagrafe.getEmail()).trim())) {
	    this.email = anagrafe.getPec();
	}
	if (anagrafe.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    this.nome = anagrafe.getNome();
	    this.cognome = anagrafe.getNominativo();
	    this.cfpi = StringUtils.isNotBlank(anagrafe.getCodicefiscale()) ? anagrafe.getCodicefiscale() : anagrafe.getPartitaiva();
	} else {
	    this.nome = anagrafe.getNominativo();
	    this.cfpi = StringUtils.isNotBlank(anagrafe.getPartitaiva()) ? anagrafe.getPartitaiva() : anagrafe.getCodicefiscale();
	}
	if (anagrafe.getIndirizzocorrispondenza() != null) {
	    this.cap = anagrafe.getCapcorrispondenza();
	    if (anagrafe.getComunecorrispondenza() != null) {
		this.localita = anagrafe.getComunecorrispondenza().getComune();
	    } else {
		this.localita = anagrafe.getCittacorrispondenza();
	    }
	    this.provincia = anagrafe.getProvinciacorrispondenza();
	    this.via = anagrafe.getIndirizzocorrispondenza();
	} else {
	    this.cap = anagrafe.getCap();
	    if (anagrafe.getComuneResidenza() != null) {
		this.localita = anagrafe.getComuneResidenza().getComune();
	    } else {
		this.localita = anagrafe.getCitta();
	    }
	    this.provincia = anagrafe.getProvincia();
	    this.via = anagrafe.getIndirizzo();
	}
	this.codiceAnagrafe = anagrafe.getId().getCodice();
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public SoggettoDebitoreType toSoggettoDebitoreType() {

	SoggettoDebitoreType soggettoDebType = new SoggettoDebitoreType();
	soggettoDebType.setCap(cap);
	soggettoDebType.setCfpi(cfpi);
	soggettoDebType.setCivico(civico);
	soggettoDebType.setCognome(cognome);
	soggettoDebType.setEmail(email);
	soggettoDebType.setLocalita(localita);
	soggettoDebType.setNome(nome);
	soggettoDebType.setProvincia(provincia);
	soggettoDebType.setStato(stato);
	soggettoDebType.setVia(via);
	return soggettoDebType;
    }
}

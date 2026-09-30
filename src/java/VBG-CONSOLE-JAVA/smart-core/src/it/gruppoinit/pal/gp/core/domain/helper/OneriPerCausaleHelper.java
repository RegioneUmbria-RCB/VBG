/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 *
 */
public class OneriPerCausaleHelper {

    //lista di tutti irecord (sia RT che localizzati) di Inventarioprocedimentioneri per un cero endoprocediemnto e per una specifica causale
    private Map<String, Inventarioprocedimentioneri> data;
    private Inventarioprocedimentioneri onereComuneBase;
    private Tipicausalioneri causale;
    private List<Responsabilicomuni> comuniGruppo;
    private Inventarioprocedimenti procedimento;

    public OneriPerCausaleHelper() {

	this.data = new HashMap<String, Inventarioprocedimentioneri>();
	this.comuniGruppo = new ArrayList<Responsabilicomuni>();
    }

    public OneriPerCausaleHelper(List<Inventarioprocedimentioneri> data) {

	this();
	this.setOneriData(data);
    }

    private void setOneriData(List<Inventarioprocedimentioneri> data) {

	if (data == null) {
	    this.data.clear();
	    this.onereComuneBase = null;
	} else {
	    for (int i = 0; i < data.size();) {
		Inventarioprocedimentioneri on = data.get(i);
		if (this.procedimento == null) {
		    this.procedimento = on.getInventarioprocedimenti();
		}
		if (this.causale == null) {
		    this.causale = on.getTipicausalioneri();
		}
		String idComuneOnere = on.getId().getIdcomune();
		//individuo il record con la configurazione regionale (default) dell'onere
		if (idComuneOnere.equals(ORMHelper.getIdcomunebase())) {
		    this.onereComuneBase = on;
		    data.remove(i);
		} else {
		    String codComune = on.getComune() != null ? on.getComune().getCodicecomune() : null;
		    this.data.put(codComune, on);
		    i++;
		}
	    }
	}
    }

    public Inventarioprocedimentioneri getOnereComuneBase() {

	return this.onereComuneBase;
    }

    public Tipicausalioneri getCausale() {

	return causale;
    }

    public Map<String, Inventarioprocedimentioneri> getOneriPerComune() {

	return this.data;
    }

    public List<Responsabilicomuni> getComuniGruppo() {

	return comuniGruppo;
    }

    public void setComuniGruppo(List<Responsabilicomuni> comuniGruppo) {

	this.comuniGruppo = comuniGruppo;
    }

    public Inventarioprocedimenti getProcedimento() {

	return this.procedimento;
    }

    public BigDecimal getImportoOnereComuneBase() {

	BigDecimal val = BigDecimal.ZERO;
	if (getOnereComuneBase() != null) {
	    val = getOnereComuneBase().getImporto();
	}
	return val;
    }

    public BigDecimal getImportoOnerePerComune(String codComune) {

	BigDecimal val = BigDecimal.ZERO;
	Inventarioprocedimentioneri on = getOneriPerComune().get(codComune);
	if (on != null) {
	    val = on.getImporto();
	}
	return val;
    }

    public String getUrlOnereComuneBase() {

	StringBuilder sb = new StringBuilder("../oneri/");
	Inventarioprocedimentioneri on = getOnereComuneBase();
	if (on != null) {
	    sb.append("view.htm?idonere=").append(on.getId().getCodice());
	} else {
	    sb.append("create.htm?idendo=").append(this.procedimento.getId().getCodice());
	    sb.append("&idcomendo=").append(this.procedimento.getId().getIdcomune());
	    sb.append("&idcausale=").append(this.causale.getId().getCodice());
	}
	return sb.toString();
    }

    public String getUrlOnerePerComune(String codComune) {

	StringBuilder sb = new StringBuilder("../oneri/");
	Inventarioprocedimentioneri on = getOneriPerComune().get(codComune);
	if (on != null) {
	    sb.append("view.htm?idonere=").append(on.getId().getCodice());
	} else {
	    sb.append("create.htm?idendo=").append(this.procedimento.getId().getCodice());
	    sb.append("&idcomendo=").append(this.procedimento.getId().getIdcomune());
	    sb.append("&idcausale=").append(this.causale.getId().getCodice());
	    sb.append("&codicecomune=").append(StringUtils.defaultString(codComune));
	}
	return sb.toString();
    }

    public String getUrlListaOneri() {

	return OneriPerCausaleHelper.getUrlListaOneri(this.procedimento);
    }

    public static String getUrlListaOneri(Inventarioprocedimenti procedimento) {

	StringBuilder sb = new StringBuilder("../oneri/list.htm");
	if (procedimento != null) {
	    sb.append("?codiceendo=").append(procedimento.getId().getCodice());
	    sb.append("&idcomendo=").append(procedimento.getId().getIdcomune());
	}
	return sb.toString();
    }
}

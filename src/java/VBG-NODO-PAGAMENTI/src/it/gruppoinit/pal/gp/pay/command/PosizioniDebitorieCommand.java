/**
 * 
 */
package it.gruppoinit.pal.gp.pay.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;

/**
 * @author francol
 *
 */
public class PosizioniDebitorieCommand extends PayBaseCommand {

    private static final long serialVersionUID = -4646529002511150629L;
    private List<PayRegistrazioniContabili> registrazioniPosizioni = new ArrayList<PayRegistrazioniContabili>();
    private List<DatiPagamentoCommand> pagamenti = new ArrayList<DatiPagamentoCommand>();
    private Map<CausaliRaggruppateBean, List<InfoCausaleBean>> mappaInfoCausali = new HashMap<CausaliRaggruppateBean, List<InfoCausaleBean>>();

    /**
     * DEVE ESSERE CHIAMATO SOLAMENTE DAL METODO CHE LO INIZIALIZZA!!
     * 
     * @return
     */
    public static PosizioniDebitorieCommand getCommand(String idRichiesta) {

	PosizioniDebitorieCommand cmd = new PosizioniDebitorieCommand();
	cmd.idRichiesta = idRichiesta;
	return cmd;
    }

    private PosizioniDebitorieCommand() {

	super();
    }

    public Map<CausaliRaggruppateBean, List<InfoCausaleBean>> getMappaInfoCausali() {

	return mappaInfoCausali;
    }

    protected PosizioniDebitorieCommand(List<PayRegistrazioniContabili> registrazioniPosizioni) {

	this();
	this.registrazioniPosizioni = registrazioniPosizioni;
    }

    public List<PayRegistrazioniContabili> getRegistrazioniPosizioni() {

	return registrazioniPosizioni;
    }

    public List<DatiPagamentoCommand> getPagamenti() {

	return this.pagamenti;
    }

    public PayPosizioniDebitorie findPosizioneByIdPSP(String idPsp) {

	if (StringUtils.isNotBlank(idPsp)) {
	    for (PayRegistrazioniContabili reg : this.registrazioniPosizioni) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    if (idPsp.equals(pos.getIdPosizionePsp())) {
			return pos;
		    }
		}
	    }
	}
	return null;
    }

    public PayPosizioniDebitorie findPosizioneById(Integer idPos) {

	if (idPos != null) {
	    for (PayRegistrazioniContabili reg : this.registrazioniPosizioni) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    if (pos.getId() != null && pos.getId().getCodice() != null && idPos.equals(pos.getId().getCodice())) {
			return pos;
		    }
		}
	    }
	}
	return null;
    }

    public DatiPagamentoType findDatiPagamentoByIdPosizione(Integer idPos) {

	if (idPos != null) {
	    for (DatiPagamentoCommand pagCmd : this.pagamenti) {
		if (idPos.equals(pagCmd.getIdPosizione())) {
		    return pagCmd.getDatiPagamento();
		}
	    }
	}
	return null;
    }
}

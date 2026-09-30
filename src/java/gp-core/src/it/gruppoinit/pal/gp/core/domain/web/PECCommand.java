/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.helper.PECAttachmentHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author francol
 * 
 */
public class PECCommand {

    private PecInbox pec;
    private Istanze istanza;
    private Movimenti movimento;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private List<PECAttachmentHelper> allegati;
    private PECAttachmentHelper corpo;
    private boolean readOnly;
    private boolean stcError;
    private boolean scompattaAllegati;
    private IstanzestradarioCommand istanzestradario;

    public PECCommand() {

	istanza = new Istanze();
	movimento = new Movimenti();
	allegati = new ArrayList<PECAttachmentHelper>();
	corpo = new PECAttachmentHelper();
	pec = new PecInbox();
	istanzestradario = new IstanzestradarioCommand();
    }

    public PecInbox getPec() {

	return pec;
    }

    public void setPec(PecInbox pec) {

	this.pec = pec;
    }

    public Anagrafe getRichiedente() {

	return this.istanza.getRichiedente();
    }

    public void setRichiedente(Anagrafe richiedente) {

	this.istanza.setRichiedente(richiedente);
    }

    public Anagrafe getTitolarelegale() {

	return this.istanza.getTitolarelegale();
    }

    public void setTitolarelegale(Anagrafe titolarelegale) {

	this.istanza.setTitolarelegale(titolarelegale);
    }

    public Alberoproc getIntervento() {

	return this.istanza.getAlberoproc();
    }

    public void setIntervento(Alberoproc intervento) {

	this.istanza.setAlberoproc(intervento);
    }

    public List<PECAttachmentHelper> getAllegati() {

	return allegati;
    }

    public void setAllegati(List<PECAttachmentHelper> allegati) {

	this.allegati = allegati;
    }

    public PECAttachmentHelper getCorpo() {

	return corpo;
    }

    public void setCorpo(PECAttachmentHelper corpo) {

	this.corpo = corpo;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public boolean isReadOnly() {

	return readOnly;
    }

    public void setReadOnly(boolean readOnly) {

	this.readOnly = readOnly;
    }

    public boolean isStcError() {

	return stcError;
    }

    public void setStcError(boolean stcError) {

	this.stcError = stcError;
    }

    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    public boolean isScompattaAllegati() {

	return scompattaAllegati;
    }

    public void setScompattaAllegati(boolean scompattaAllegati) {

	this.scompattaAllegati = scompattaAllegati;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public IstanzestradarioCommand getIstanzestradario() {

	return istanzestradario;
    }

    public void setIstanzestradario(IstanzestradarioCommand istanzestradario) {

	this.istanzestradario = istanzestradario;
    }
}

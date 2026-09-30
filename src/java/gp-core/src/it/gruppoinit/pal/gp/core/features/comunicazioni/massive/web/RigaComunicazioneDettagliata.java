package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class RigaComunicazioneDettagliata extends RigaComunicazione {

    @XmlElement(name = "fkidprotocollo")
    private Integer fkIdProtocollo;
    @XmlElement(name = "estremiprotocollo")
    private String estremiProtocollo;
    @XmlElement(name = "codicioggettoallegati")
    private List<Integer> codiciOggettoAllegati;
    @XmlElement(name = "firmatari")
    private List<Firmatario> firmatari;
    @XmlElement(name = "mail_inviate")
    private List<DettagliMailComunicazione> mailInviate;

    public Integer getFkIdProtocollo() {

	return fkIdProtocollo;
    }

    public void setFkIdProtocollo(Integer fkIdProtocollo) {

	this.fkIdProtocollo = fkIdProtocollo;
    }

    public String getEstremiProtocollo() {

	return estremiProtocollo;
    }

    public void setEstremiProtocollo(String estremiProtocollo) {

	this.estremiProtocollo = estremiProtocollo;
    }

    public List<Integer> getCodiciOggettoAllegati() {

	if (codiciOggettoAllegati == null) {
	    this.codiciOggettoAllegati = new ArrayList<Integer>();
	}
	return codiciOggettoAllegati;
    }

    public void setCodiciOggettoAllegati(List<Integer> codiciOggettoAllegati) {

	this.codiciOggettoAllegati = codiciOggettoAllegati;
    }

    public List<Firmatario> getFirmatari() {

	if (firmatari == null) {
	    this.firmatari = new ArrayList<Firmatario>();
	}
	return firmatari;
    }

    public void setFirmatari(List<Firmatario> firmatari) {

	this.firmatari = firmatari;
    }

    public static RigaComunicazioneDettagliata FromDatiDB(MassiveDettaglio riga, List<Integer> codiciOggettoAllegati,
	    List<MassiveDettDocdafirmare> docDaFirmare, List<DettagliMailComunicazione> dettagliMailRiga) {

	if (riga == null || riga.getId() == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo RigaComunicazione senza passsare il parametro riga valido");
	}
	RigaComunicazioneDettagliata retval = new RigaComunicazioneDettagliata();
	RigaComunicazione rm = RigaComunicazione.FromMassiveDettaglio(riga);
	retval.setId(rm.getId());
	retval.setCodiceAnagrafe(rm.getCodiceAnagrafe());
	retval.setDestinatario(rm.getDestinatario());
	retval.setEmail(rm.getEmail());
	retval.setPec(rm.getPec());
	retval.setModificaMail(rm.isModificaMail());
	retval.setErrore(rm.getErrore());
	retval.setDescrizioneStato(rm.getDescrizioneStato());
	if (StringUtils.isNotBlank(riga.getNumeroprotocollo())) {
	    StringBuilder protocollo = new StringBuilder();
	    protocollo.append(riga.getNumeroprotocollo());
	    protocollo.append(" del ");
	    protocollo.append(Utilities.formatDate(riga.getDataprotocollo(), false));
	    retval.setEstremiProtocollo(protocollo.toString());
	}
	retval.setCodiciOggettoAllegati(codiciOggettoAllegati);
	Map<String, Boolean> firmatari = new HashMap<String, Boolean>();
	for (MassiveDettDocdafirmare documento : docDaFirmare) {
	    String responsabile = documento.getDocumentiDaFirmare().getFirmatario().getResponsabile();
	    Boolean firmaCompleta = documento.getDocumentiDaFirmare().isFirmatoConSuccesso();
	    if (firmatari.get(responsabile) != null && firmatari.get(responsabile).equals(Boolean.FALSE)) {
		continue;
	    }
	    firmatari.put(responsabile, firmaCompleta);
	}
	for (Map.Entry<String, Boolean> firmatario : firmatari.entrySet()) {
	    retval.getFirmatari().add(Firmatario.FromFirmatari(firmatario.getKey(), firmatario.getValue()));
	}
	if (dettagliMailRiga != null) {
	    retval.setMailInviate(dettagliMailRiga);
	}
	return retval;
    }

    public List<DettagliMailComunicazione> getMailInviate() {

	return mailInviate;
    }

    public void setMailInviate(List<DettagliMailComunicazione> mailInviate) {

	this.mailInviate = mailInviate;
    }
    
}

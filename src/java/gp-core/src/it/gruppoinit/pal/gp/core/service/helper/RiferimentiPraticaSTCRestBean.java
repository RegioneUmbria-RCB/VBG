package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.init.sigepro.rte.types.SportelloType;

public class RiferimentiPraticaSTCRestBean {

    private SportelloType mittente;
    private SportelloType destinatario;
    private String id_pratica;
    private String numero_pratica;
    private String data_pratica;
    private String numero_protocollo_generale;
    private String data_protocollo_generale;
    private CodiceDescrizioneBean errore;

    public SportelloType getMittente() {

	return mittente;
    }

    public void setMittente(SportelloType mittente) {

	this.mittente = mittente;
    }

    public SportelloType getDestinatario() {

	return destinatario;
    }

    public void setDestinatario(SportelloType destinatario) {

	this.destinatario = destinatario;
    }

    public String getId_pratica() {

	return id_pratica;
    }

    public void setId_pratica(String id_pratica) {

	this.id_pratica = id_pratica;
    }

    public String getNumero_pratica() {

	return numero_pratica;
    }

    public void setNumero_pratica(String numero_pratica) {

	this.numero_pratica = numero_pratica;
    }

    public String getNumero_protocollo_generale() {

	return numero_protocollo_generale;
    }

    public void setNumero_protocollo_generale(String numero_protocollo_generale) {

	this.numero_protocollo_generale = numero_protocollo_generale;
    }

    public String getData_pratica() {

	return data_pratica;
    }

    public void setData_pratica(String data_pratica) {

	this.data_pratica = data_pratica;
    }

    public String getData_protocollo_generale() {

	return data_protocollo_generale;
    }

    public void setData_protocollo_generale(String data_protocollo_generale) {

	this.data_protocollo_generale = data_protocollo_generale;
    }

    public CodiceDescrizioneBean getErrore() {

	return errore;
    }

    public void setErrore(CodiceDescrizioneBean errore) {

	this.errore = errore;
    }
}

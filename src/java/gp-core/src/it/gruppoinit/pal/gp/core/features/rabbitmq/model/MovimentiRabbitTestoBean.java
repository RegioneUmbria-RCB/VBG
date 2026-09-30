package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

@XmlRootElement
public class MovimentiRabbitTestoBean {

    @XmlElement
    private String titolo;
    @XmlElement
    private String sottoTitolo;
    @XmlElement
    private String messaggio;
    @XmlElement
    private CodiceDescrizioneBean errore;

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getSottoTitolo() {

	return sottoTitolo;
    }

    public void setSottoTitolo(String sottoTitolo) {

	this.sottoTitolo = sottoTitolo;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public CodiceDescrizioneBean getErrore() {

	return errore;
    }

    public void setErrore(CodiceDescrizioneBean errore) {

	this.errore = errore;
    }

    public static MovimentiRabbitTestoBean fromMailTipo(Mailtipo mailtipo, String titolo, String sottoTitolo) {

	MovimentiRabbitTestoBean ret = new MovimentiRabbitTestoBean();
	ret.setTitolo(mailtipo.getOggetto());
	if (StringUtils.isNotBlank(titolo)) {
	    ret.setTitolo(titolo);
	}
	ret.setSottoTitolo(sottoTitolo);
	ret.setMessaggio(mailtipo.getCorpo());
	return ret;
    }
}

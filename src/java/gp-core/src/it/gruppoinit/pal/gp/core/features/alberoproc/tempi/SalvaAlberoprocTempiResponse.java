package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalvaAlberoprocTempiResponse {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "messaggio")
    private String messaggio;
    @XmlElement(name = "codiceintervento")
    private Integer codiceIntervento;
    @XmlElement(name = "idtestata")
    private Integer idTestata;

    public static SalvaAlberoprocTempiResponse KO(String messaggio) {

	SalvaAlberoprocTempiResponse response = new SalvaAlberoprocTempiResponse();
	response.setEsito("KO");
	response.setMessaggio(messaggio);
	return response;
    }

    public static SalvaAlberoprocTempiResponse OK(Integer codiceIntervento, Integer idTestata) {

	SalvaAlberoprocTempiResponse response = new SalvaAlberoprocTempiResponse();
	response.setEsito("OK");
	response.setCodiceIntervento(codiceIntervento);
	response.setIdTestata(idTestata);
	return response;
    }

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }

    public Integer getIdTestata() {

	return idTestata;
    }

    public void setIdTestata(Integer idTestata) {

	this.idTestata = idTestata;
    }
}

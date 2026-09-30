package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoRicaricaBorsellino {

    @XmlElement(name = "uuid_borsellino")
    private String uuidBorsellino;
    @XmlElement(name = "id_ricarica")
    private Integer idRicarica;
    @XmlElement(name = "esito")
    private EsitoAggiornamentoBorsellino esito;
    @XmlElement(name = "posizione_debitoria")
    private PosizioneDebitoriaBorsellinoRest posizioneDebitoria;

    protected EsitoRicaricaBorsellino() {

	super();
    }

    public EsitoRicaricaBorsellino(String uuidBorsellino) {

	this.uuidBorsellino = uuidBorsellino;
    }

    public Integer getIdRicarica() {

	return idRicarica;
    }

    public void setIdRicarica(Integer idRicarica) {

	this.idRicarica = idRicarica;
    }

    public EsitoAggiornamentoBorsellino getEsito() {

	return esito;
    }

    public void setEsito(EsitoAggiornamentoBorsellino esito) {

	this.esito = esito;
    }

    public PosizioneDebitoriaBorsellinoRest getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(PosizioneDebitoriaBorsellinoRest posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }

    public String getUuidBorsellino() {

	return uuidBorsellino;
    }

    public void setUuidBorsellino(String uuidBorsellino) {

	this.uuidBorsellino = uuidBorsellino;
    }
}

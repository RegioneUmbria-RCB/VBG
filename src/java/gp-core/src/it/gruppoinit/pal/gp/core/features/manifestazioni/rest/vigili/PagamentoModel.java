package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagamentoModel {

    @XmlElement
    private StatoPagamentoEnum stato;
    @XmlElement
    private Integer idAutorizzazione;
    @XmlElement
    private PosizioneDebitoriaModel posizioneDebitoria;

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    public StatoPagamentoEnum getStato() {

	return stato;
    }

    public void setStato(StatoPagamentoEnum stato) {

	this.stato = stato;
    }

    public PosizioneDebitoriaModel getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(PosizioneDebitoriaModel posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }
}

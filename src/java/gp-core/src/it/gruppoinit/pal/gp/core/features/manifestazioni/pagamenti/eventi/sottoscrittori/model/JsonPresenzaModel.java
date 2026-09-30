package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model;

import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonPresenzaModel {

    @XmlElement(name = "id_mercatipresenzed")
    private Integer id;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "mercato")
    private String mercato;
    @XmlElement(name = "posteggio")
    private String posteggio;
    @XmlElement(name = "occupante")
    private String occupante;
    @XmlElement(name = "spuntista_presente")
    private boolean spuntista;
    @XmlElement(name = "concessionario_presente")
    private boolean concessionarioPresente;
    @XmlElement(name = "autorizzazione")
    private String autorizzazione;
    @XmlElement(name = "posizione_debitoria")
    private String posizioneDebitoria;

    public static JsonPresenzaModel fromMercatipresenzeD(MercatipresenzeD giornata) {

	if (giornata == null) {
	    return new JsonPresenzaModel();
	}
	JsonPresenzaModel model = new JsonPresenzaModel();
	if (giornata.getId() != null) {
	    model.id = giornata.getId().getCodice();
	}
	if (giornata.getMercatiPresenzeT() != null) {
	    model.data = giornata.getMercatiPresenzeT().getDataRegistrazione();
	    if (giornata.getMercatiPresenzeT().getMercato() != null) {
		model.mercato = giornata.getMercatiPresenzeT().getMercato().getDescrizione();
	    }
	}
	if (giornata.getPosteggio() != null) {
	    model.posteggio = giornata.getPosteggio().getCodiceposteggio();
	}
	if (giornata.getOccupante() != null) {
	    model.occupante = giornata.getOccupante().toString();
	}
	model.spuntista = giornata.isSpuntista();
	model.concessionarioPresente = giornata.isConcessionarioPresente();
	if (giornata.getAutorizzazioni() != null) {
	    model.autorizzazione = giornata.getAutorizzazioni().getTransientEstremiAut();
	}
	if (giornata.getDettPosizioneDebitoria() != null) {
	    model.posizioneDebitoria = giornata.getDettPosizioneDebitoria().toString();
	}
	return model;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}

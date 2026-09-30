package it.gruppoinit.pal.gp.pay.service.helper.rabbit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "")
public class RabbitAggiornaStatoPosizioneDebitoria extends RabbitPosizioneDebitoriaInfo {

    private RabbitAggiornaStatoPosizioneDebitoria() {

	super();
    }

    @XmlElement
    private String stato;

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @XmlElement
    private String dataEvento;

    public String getDataEvento() {

	return dataEvento;
    }

    /**
     * Lo lascio privato perchè lo mando come stringa con il formato corretto
     * 
     * @param dataEvento
     */
    private void setDataEvento(String dataEvento) {

	this.dataEvento = dataEvento;
    }

    public static RabbitAggiornaStatoPosizioneDebitoria fromPayPosizioneStato(String alias //
	    , String cfEnteCreditore //
	    , String cfPiva //
	    , String idcomune //
	    , String nominativo //
	    , Set<String> riferimentoClient //
	    , String stato //
	    , String uuid //
	    , Date dataEvento) {

	// 
	// rilancia  Errore could not initialize proxy - no Session> org.hibernate.LazyInitializationException: could not initialize proxy - no Session
	RabbitAggiornaStatoPosizioneDebitoria ret = new RabbitAggiornaStatoPosizioneDebitoria();
	ret.setAlias(alias);
	ret.setCfEnteCreditore(cfEnteCreditore);
	ret.setCfPiva(cfPiva);
	ret.setIdcomune(idcomune);
	ret.setNominativo(nominativo);
	ret.setRiferimentoClient(new ArrayList<String>(riferimentoClient));
	ret.setStato(stato);
	ret.setUuid(uuid);
	ret.setDataEvento(Utilities.formatDateWithJsonInterchange(dataEvento));
	return ret;
    }
}

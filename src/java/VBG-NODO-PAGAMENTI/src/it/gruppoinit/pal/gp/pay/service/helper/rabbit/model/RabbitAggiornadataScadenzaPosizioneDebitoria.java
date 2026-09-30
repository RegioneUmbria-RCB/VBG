package it.gruppoinit.pal.gp.pay.service.helper.rabbit.model;

import java.util.ArrayList;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "")
public class RabbitAggiornadataScadenzaPosizioneDebitoria extends RabbitPosizioneDebitoriaInfo {

    @XmlElement
    private String dataScadenza;

    public static RabbitAggiornadataScadenzaPosizioneDebitoria fromPayPosizioneDebitoria(PayPosizioniDebitorie entity, String alias,
	    Set<String> riferimentiClient) {

	RabbitAggiornadataScadenzaPosizioneDebitoria ret = new RabbitAggiornadataScadenzaPosizioneDebitoria();
	ret.setAlias(alias);
	ret.setCfEnteCreditore(entity.getProfiloEnte().getCfCodiceProfilo());
	ret.setCfPiva(entity.getSoggettoDebitore().getCfPi());
	ret.setIdcomune(entity.getId().getIdcomune());
	ret.setNominativo(entity.getSoggettoDebitore().getDenominazioneCompleta());
	ret.setRiferimentoClient(new ArrayList(riferimentiClient));
	ret.setUuid(entity.getUuid());
	ret.setDataScadenza(Utilities.formatDateWithJsonInterchange(entity.getDataScadenza()));
	return ret;
    }

    public String getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {

	this.dataScadenza = dataScadenza;
    }
}

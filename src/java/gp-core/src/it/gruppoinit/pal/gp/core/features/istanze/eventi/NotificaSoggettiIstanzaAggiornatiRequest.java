package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;

public class NotificaSoggettiIstanzaAggiornatiRequest extends NotificaCambioStatoRequest {

    public NotificaSoggettiIstanzaAggiornatiRequest(Integer codiceIstanza, String software, String uuidPratica) {

	super(codiceIstanza, software, uuidPratica, RabbitTopicEnum.BACKEND_PRATICHE_DESTINATARI_AGGIORNATI);
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit;

import java.util.List;

import javax.xml.bind.JAXBException;

import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.TopicType;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;

public interface ITipimovimentoRabbitService {

    List<TipimovimentoRabbit> findTipimovRabbitByTipomov(String tipomovimento);

    public void insert(TipimovimentoRabbit tipimovimentoRabbit);

    TipimovimentoRabbit findById(TipimovimentoRabbitId id);

    void delete(TipimovimentoRabbit tmr);

    void update(TipimovimentoRabbit tipimovimentoRabbit);

    List<TopicType> findListaTopic() throws JAXBException;

    boolean checkTipimovRabbitByTipomovAndTopic(String tipomovimento, RabbitTopicEnum topic);
}

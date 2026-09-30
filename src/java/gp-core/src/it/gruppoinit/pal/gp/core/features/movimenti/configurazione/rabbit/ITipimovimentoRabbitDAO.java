package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;

public interface ITipimovimentoRabbitDAO {

    void insert(TipimovimentoRabbit tipimovimentoRabbit);

    List<TipimovimentoRabbit> findTipimovRabbitByTipomov(String tipomovimento);

    TipimovimentoRabbit findById(TipimovimentoRabbitId id);

    void delete(TipimovimentoRabbit tmr);

    void update(TipimovimentoRabbit tipimovimentoRabbit);

    boolean checkTipimovRabbitByTipomovAndTopic(String tipomovimento, RabbitTopicEnum topic);

    Integer recuperaTestoTipodaMovimentoETopic(String tipoMovimento, String topic);
}

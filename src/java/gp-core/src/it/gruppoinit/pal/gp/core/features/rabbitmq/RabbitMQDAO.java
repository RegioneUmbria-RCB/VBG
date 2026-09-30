package it.gruppoinit.pal.gp.core.features.rabbitmq;

public interface RabbitMQDAO {

    Integer recuperaTestoTipodaMovimentoETopic(String tipoMovimento, String topic);
}

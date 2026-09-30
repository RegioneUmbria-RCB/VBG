package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.TopicType;
import it.gruppoinit.pal.gp.core.features.rabbitmq.TopicTypeList;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class TipimovimentoRabbitServiceImpl implements ITipimovimentoRabbitService {

    private static final Logger log = LoggerFactory.getLogger(TipimovimentoRabbitServiceImpl.class);
    @Autowired
    private ITipimovimentoRabbitDAO tipimovimentoRabbitDAO;

    @Override
    public List<TipimovimentoRabbit> findTipimovRabbitByTipomov(String tipomovimento) {

	return tipimovimentoRabbitDAO.findTipimovRabbitByTipomov(tipomovimento);
    }

    @Override
    public void insert(TipimovimentoRabbit tipimovimentoRabbit) {

	if (isValido(tipimovimentoRabbit)) {
	    this.tipimovimentoRabbitDAO.insert(tipimovimentoRabbit);
	} else {
	    throw new RuntimeException("Topic già configurata per il movimento");
	}
    }

    private boolean isValido(TipimovimentoRabbit tipimovimentoRabbit) {

	boolean isValido = true;
	List<TipimovimentoRabbit> list = this.tipimovimentoRabbitDAO.findTipimovRabbitByTipomov(tipimovimentoRabbit.getId().getFkTipimovimento());
	if (list == null) {
	    return true;
	} else {
	    for (TipimovimentoRabbit tmr : list) {
		if (tipimovimentoRabbit.getId().getTopic().equalsIgnoreCase(tmr.getId().getTopic())) {
		    isValido = false;
		    break;
		}
	    }
	}
	return isValido;
    }

    @Override
    public TipimovimentoRabbit findById(TipimovimentoRabbitId id) {

	return this.tipimovimentoRabbitDAO.findById(id);
    }

    @Override
    public void delete(TipimovimentoRabbit tmr) {

	this.tipimovimentoRabbitDAO.delete(tmr);
    }

    @Override
    public void update(TipimovimentoRabbit tipimovimentoRabbit) {

	this.tipimovimentoRabbitDAO.update(tipimovimentoRabbit);
    }

    @Override
    public List<TopicType> findListaTopic() throws JAXBException {

	List<TopicType> elenco = new ArrayList<TopicType>();
	for (String topic : RabbitTopicEnum.findListaTopicForClient()) {
	    elenco.add(new TopicType(topic));
	}
	return elenco;
    }

    @Override
    public boolean checkTipimovRabbitByTipomovAndTopic(String tipomovimento, RabbitTopicEnum topic) {

	return tipimovimentoRabbitDAO.checkTipimovRabbitByTipomovAndTopic(tipomovimento, topic);
    }
}

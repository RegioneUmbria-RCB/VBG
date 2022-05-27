package com.paevolution.appioproducer.core.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;
import com.paevolution.appioproducer.core.repository.helper.MessageToSendQueryHelper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MessageToSendRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MessageToSendQueryHelper messageToSendQueryHelper;

    public List<MessageToSendHelper> findMessageToSend() {

	log.debug("findMessageToSend: start");
	List<MessageToSendHelper> listMessageToSendHelpers = new ArrayList<MessageToSendHelper>();
	String sql = messageToSendQueryHelper.buildQuery();
	Query q = entityManager.createNativeQuery(sql);
	List<Object[]> messageToSendList = q.getResultList();
	for (Object[] msg : messageToSendList) {
	    Object[] obj = (Object[]) msg;
	    MessageToSendHelper msgToSendHelper = buildMsgHelperFromQueryResult(obj);
	    listMessageToSendHelpers.add(msgToSendHelper);
	}
	return listMessageToSendHelpers;
    }

    private MessageToSendHelper buildMsgHelperFromQueryResult(Object[] obj) {

	return MessageToSendHelper.builder().idcomune((String) obj[0]).codicecomune((String) obj[1]).software((String) obj[2])
		.codiceistanza(obj[3] != null ? ((BigDecimal) obj[3]).intValue() : null)
		.codicestato(obj[4] != null ? ((BigDecimal) obj[4]).intValue() : null).numeroprotocollo((String) obj[5]).dataprotocollo((Date) obj[6])
		.numeroistanza((String) obj[7]).data((Date) obj[8]).lavori((String) obj[9]).denominazioneServizio((String) obj[10])
		.urlPortaleServizi((String) obj[11]).destinatario((String) obj[12])
		.destinatarioCodiceanagrafe(obj[13] != null ? ((BigDecimal) obj[13]).intValue() : null).destinatarioCodicefiscale((String) obj[14])
		.codicemovimento(obj[15] != null ? ((BigDecimal) obj[15]).intValue() : null).movimento((String) obj[16]).build();
    }
}

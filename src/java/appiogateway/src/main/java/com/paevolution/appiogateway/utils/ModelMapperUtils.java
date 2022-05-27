package com.paevolution.appiogateway.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.paevolution.appiogateway.connector.ioitalia.client.model.LimitedProfile;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageContent;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessageDefaultAddresses;
import com.paevolution.appiogateway.connector.ioitalia.client.model.PaymentData;
import com.paevolution.appiogateway.connector.ioitalia.client.model.PrescriptionData;
import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.dto.ProblemDTO;
import com.paevolution.appiogateway.web.model.request.MessaggiRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModelMapperUtils {

    public static NewMessage newMessageFromMessaggiDTO(MessaggiDTO messaggiDTO) {

	NewMessage newMessage = new NewMessage();
	newMessage.setTimeToLive(messaggiDTO.getTimeToLive());
	/// Setting MessageContent
	MessageContent content = new MessageContent();
	content.setSubject(messaggiDTO.getSubject());
	content.setMarkdown(messaggiDTO.getMarkdown());
	if (messaggiDTO.getAmount() != null && StringUtils.isNotBlank(messaggiDTO.getNoticeNumber())) {
	    PaymentData paymentData = new PaymentData();
	    paymentData.setAmount(messaggiDTO.getAmount());
	    paymentData.setInvalidAfterDueDate(messaggiDTO.getInvalidAfterDueDate());
	    paymentData.setNoticeNumber(messaggiDTO.getNoticeNumber());
	    content.setPaymentData(paymentData);
	}
	if (StringUtils.isNotBlank(messaggiDTO.getNre())) {
	    PrescriptionData prescriptionData = new PrescriptionData();
	    prescriptionData.setNre(messaggiDTO.getNre());
	    prescriptionData.setIup(messaggiDTO.getIup());
	    prescriptionData.setPrescriberFiscalCode(messaggiDTO.getPrescriberFiscalCode());
	    content.setPrescriptionData(prescriptionData);
	}
	if (messaggiDTO.getDueDate() != null) {
	    content.setDueDate(messaggiDTO.getDueDate());
	    // try {
	    // content.setDueDate(Utils.convertDateToSQLTimestamp(messaggiDTO.getDueDate()));
	    // } catch (ParseException ex) {
	    // log.error("newMessageFromMessaggiDTO# Errore durante il parsing del campo
	    // due_date: {}", ex);
	    // }
	}
	newMessage.setContent(content);
	if (StringUtils.isNotBlank(messaggiDTO.getEmail())) {
	    NewMessageDefaultAddresses defaultAddress = new NewMessageDefaultAddresses();
	    defaultAddress.setEmail(messaggiDTO.getEmail());
	    newMessage.setDefaultAddresses(defaultAddress);
	}
	newMessage.setFiscalCode(messaggiDTO.getFiscalCode());
	return newMessage;
    }

    public static MessaggiDTO newMessaggiDTOFromMessaggiRequest(MessaggiRequest request) {

	MessaggiDTO messaggiDTO = new MessaggiDTO();
	if (StringUtils.isNotBlank(request.getIdcomune())) {
	    messaggiDTO.setIdcomune(request.getIdcomune());
	}
	if (StringUtils.isNotBlank(request.getCodicecomune())) {
	    messaggiDTO.setCodicecomune(request.getCodicecomune());
	}
	if (StringUtils.isNotBlank(request.getSoftware())) {
	    messaggiDTO.setSoftware(request.getSoftware());
	}
	if (request.getTimeToLive() != null) {
	    messaggiDTO.setTimeToLive(request.getTimeToLive());
	}
	messaggiDTO.setSubject(request.getSubject());
	messaggiDTO.setMarkdown(request.getMarkdown());
	if (StringUtils.isNotBlank(request.getDueDate())) {
	    messaggiDTO.setDueDate(Utils.convertStringToSQLTimestamp(request.getDueDate()));
	}
	if (StringUtils.isNotBlank(request.getEmail())) {
	    messaggiDTO.setEmail(request.getEmail());
	}
	if (StringUtils.isNotBlank(request.getFiscalCode())) {
	    messaggiDTO.setFiscalCode(request.getFiscalCode());
	}
	if (StringUtils.isNotBlank(request.getMessageId())) {
	    messaggiDTO.setMessageId(request.getMessageId());
	}
	return messaggiDTO;
    }
    /// FIXME: Usando una matching strategy LOOSE:
    /// - i token possono corrispondere in qualsiasi ordine
    /// - il nome dell'ultima proprieta' dell'oggetto destinazione deve avere tutti
    /// i token che corrispondono
    /// - il nome dell'ultima proprieta' dell'oggetto sorgente deve avere almeno uno
    /// dei token che corrisponde
    /*
     * public static void updateMessaggiDTO(MessaggiDTO target,
     * MessageResponseWithContent source) {
     * 
     * ModelMapper mapper = new ModelMapper();
     * mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE); if
     * (source != null) { mapper.addMappings(new
     * PropertyMap<MessageResponseWithContent, MessaggiDTO>() {
     * 
     * @Override protected void configure() {
     * 
     * map().setCreatedAt(source.getMessage().getCreatedAt()); if
     * (source.getNotification() != null) { if
     * (StringUtils.isNotBlank(source.getNotification().getWebhook())) {
     * ////map().setWebhookNotification(source.getNotification().getWebhook());
     * map().setNotificationWebhook(this.source.getNotification().getWebhook());
     * //map(this.source.getNotification().getWebhook(),
     * this.destination.getNotificationWebhook()); } if
     * (StringUtils.isNotBlank(source.getNotification().getEmail())) {
     * map().setNotificationEmail(source.getNotification().getEmail()); } } if
     * (StringUtils.isNotBlank(source.getStatus())) {
     * map().setStatus(source.getStatus()); } } }); mapper.map(source, target); } }
     */

    public static void updateMessaggiDTO(MessaggiDTO target, ProblemDTO source) {

	ModelMapper mapper = new ModelMapper();
	mapper.map(source, target);
    }

    public static void updateMessaggiDTO(MessaggiDTO msgDTO, LimitedProfile profile) {

	msgDTO.setSenderAllowed(profile.getSenderAllowed());
	String preferredLanguages = (profile.getPreferredLanguages() != null && !profile.getPreferredLanguages().isEmpty())
		? StringUtils.join(profile.getPreferredLanguages(), ";")
		: "";
	msgDTO.setPreferredLanguages(preferredLanguages);
    }

    public static void updateMessaggiDTOWithNotificationStatus(MessaggiDTO target, MessageResponseWithContent src) {

	if (src.getMessage() != null && src.getMessage().getCreatedAt() != null) {
	    target.setCreatedAt(src.getMessage().getCreatedAt());
	}
	if (src.getNotification() != null) {
	    if (StringUtils.isNotBlank(src.getNotification().getWebhook())) {
		target.setNotificationWebhook(src.getNotification().getWebhook());
	    }
	    if (StringUtils.isNotBlank(src.getNotification().getEmail())) {
		target.setNotificationEmail(src.getNotification().getEmail());
	    }
	}
	if (StringUtils.isNotBlank(src.getStatus())) {
	    target.setStatus(src.getStatus());
	}
    }

    /**
     * 
     * Rersituisce una lista di MessaggiDTO a partire da una lista di Messaggi
     */
    public static List<MessaggiDTO> mapListMessaggiToListMessaggiDTO(List<Messaggi> messaggis) {

	ModelMapper modelMapper = new ModelMapper();
	modelMapper.addMappings(new PropertyMap<Messaggi, MessaggiDTO>() {

	    @Override
	    protected void configure() {

		map().setConnettore(source.getServizi().getTipoConnettore().getNome());
		map().setStatoMessaggio(source.getStatoMessaggio().getNome());
		map().setChiavePrimaria(source.getServizi().getChiavePrimaria());
	    }
	});
	List<MessaggiDTO> messaggiDTOs = mapList(messaggis, MessaggiDTO.class, modelMapper);
	return messaggiDTOs;
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {

	return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }
}

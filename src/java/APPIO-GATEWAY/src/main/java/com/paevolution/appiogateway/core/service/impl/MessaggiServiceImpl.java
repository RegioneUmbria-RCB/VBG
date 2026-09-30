package com.paevolution.appiogateway.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.domain.Servizi;
import com.paevolution.appiogateway.core.domain.StatoMessaggio;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.repository.MessaggiRepository;
import com.paevolution.appiogateway.core.repository.ServiziRepository;
import com.paevolution.appiogateway.core.repository.StatoMessaggioRepository;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.exceptions.DataIntegrationException;
import com.paevolution.appiogateway.utils.FieldEnum;
import com.paevolution.appiogateway.utils.ModelMapperUtils;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessaggiServiceImpl implements MessaggiService {

    private static final int PUBLIC_ID_LENGTH = 26;
    private MessaggiRepository messaggiRepository;
    private ServiziRepository serviziRepository;
    private StatoMessaggioRepository statoMessaggioRepository;

    public MessaggiServiceImpl() {

    }

    public MessaggiServiceImpl(MessaggiRepository messaggiRepository) {

	setMessaggiRepository(messaggiRepository);
    }

    @Autowired
    public void setMessaggiRepository(MessaggiRepository messaggiRepository) {

	this.messaggiRepository = messaggiRepository;
    }

    @Autowired
    public void setServiziRepository(ServiziRepository serviziRepository) {

	this.serviziRepository = serviziRepository;
    }

    @Autowired
    public void setStatoMessaggioRepository(StatoMessaggioRepository statoMessaggioRepository) {

	this.statoMessaggioRepository = statoMessaggioRepository;
    }

    @Override
    public List<MessaggiDTO> findAllMessaggi() throws Exception {

	List<Messaggi> messaggis = IterableUtils.toList(this.messaggiRepository.findAll());
	List<MessaggiDTO> messaggiDTOs = ModelMapperUtils.mapListMessaggiToListMessaggiDTO(messaggis);
	return messaggiDTOs;
    }

    @Override
    public void updateIdTransazione(Long id, String idTransazione) {

	this.messaggiRepository.updateIdTransazione(id, idTransazione);
    }

    @Override
    public Page<Messaggi> allPaginatedMessages(Pageable pageable) {

	return messaggiRepository.findAll(pageable);
    }

    /**
     * 
     * metodo che effettua il salvataggio di un oggetto Messaggi a partire da un MessaggiDTO
     * 
     * @throws DataIntegrationException
     */
    @Override
    public MessaggiDTO createMessage(MessaggiDTO messaggiDTO) throws DataIntegrationException {

	ModelMapper mapper = modelMapper();
	// Verifica che il messaggio non sia già stato inviato
	if (messaggiRepository.findByMessageId(messaggiDTO.getMessageId()).isPresent()) {
	    throw new RuntimeException("Il messaggio con [id_messaggio_mittente=" + messaggiDTO.getMessageId() + "] è già presente nel sistema.");
	}
	Messaggi messageToStore = buildFromDTO(messaggiDTO);
	Messaggi storedMessage = messaggiRepository.save(messageToStore);
	mapper.addMappings(new PropertyMap<Messaggi, MessaggiDTO>() {

	    @Override
	    protected void configure() {

		map().setConnettore((source.getServizi().getTipoConnettore().getNome()));
		map().setChiavePrimaria(source.getServizi().getChiavePrimaria());
		map().setChiaveScondaria(source.getServizi().getChiaveSecondaria());
		map().setStatoMessaggio(source.getStatoMessaggio().getNome());
	    }
	});
	MessaggiDTO returnValue = mapper.map(storedMessage, MessaggiDTO.class);
	return returnValue;
    }

    @Override
    public Optional<MessaggiDTO> findByMessageId(String messageId) {

	Optional<Messaggi> messaggioOptional = messaggiRepository.findByMessageId(messageId);
	MessaggiDTO returnValue = null;
	if (messaggioOptional.isPresent()) {
	    ModelMapper mapper = modelMapper();
	    mapper.addMappings(new PropertyMap<Messaggi, MessaggiDTO>() {

		@Override
		protected void configure() {

		    map().setIdServizio(source.getServizi().getIdServizio());
		    map().setConnettore(source.getServizi().getTipoConnettore().getNome());
		    map().setChiavePrimaria(source.getServizi().getChiavePrimaria());
		    map().setStatoMessaggio(source.getStatoMessaggio().getNome());
		}
	    });
	    returnValue = mapper.map(messaggioOptional.get(), MessaggiDTO.class);
	}
	return Optional.ofNullable(returnValue);
    }

    @Override
    public void updateFromDTOSelectively(MessaggiDTO msgDTO, FieldEnum field) throws Exception {

	Optional<Messaggi> msgOptional = messaggiRepository.findById(msgDTO.getId());
	if (!msgOptional.isPresent()) {
	    throw new Exception("updateMessaggi# Messaggio da aggiornare con [id=" + msgDTO.getId() + "] non trovato.");
	}
	Messaggi messageToUpdate = msgOptional.get();
	Long id = messageToUpdate.getId();
	switch (field) {
	case ID_TRANSAZIONE:
	    messageToUpdate.setIdTransazione(msgDTO.getIdTransazione());
	    messaggiRepository.updateIdTransazione(messageToUpdate.getId(), messageToUpdate.getIdTransazione());
	    break;
	case SENDER_ALLOWED:
	    messageToUpdate.setSenderAllowed(msgDTO.getSenderAllowed());
	    messaggiRepository.updateSenderAllowed(id, messageToUpdate.getSenderAllowed());
	    break;
	case PREFERRED_LANGUAGES:
	    messageToUpdate.setPreferredLanguages(msgDTO.getPreferredLanguages());
	    messaggiRepository.updatePreferredLanguages(id, messageToUpdate.getPreferredLanguages());
	    break;
	case CREATED_AT:
	    messageToUpdate.setCreatedAt(msgDTO.getCreatedAt());
	    messaggiRepository.updateCreatedAt(id, messageToUpdate.getCreatedAt());
	    break;
	case WEBHOOK_NOTIFICATION:
	    messageToUpdate.setWebhookNotification(msgDTO.getNotificationWebhook());
	    messaggiRepository.updateWebhookNotification(id, messageToUpdate.getWebhookNotification());
	    break;
	case EMAIL_NOTIFICATION:
	    messageToUpdate.setEmailNotification(msgDTO.getNotificationEmail());
	    messaggiRepository.updateEmailNotification(id, messageToUpdate.getEmailNotification());
	    break;
	case STATUS:
	    messageToUpdate.setStatus(msgDTO.getStatus());
	    messaggiRepository.updateStatus(id, messageToUpdate.getStatus());
	    break;
	case STATO_MESSAGGIO:
	    Optional<StatoMessaggio> statoMessaggioOpt = statoMessaggioRepository.findByNome(msgDTO.getStatoMessaggio());
	    if (statoMessaggioOpt.isPresent()) {
		messageToUpdate.setStatoMessaggio(statoMessaggioOpt.get());
		messaggiRepository.updateStatoMessaggio(id, statoMessaggioOpt.get());
	    }
	    break;
	default:
	    log.info("updateFromDTOSelectively# Nessun campo da aggiornare.");
	    break;
	}
    }

    @Override
    public void multiFieldUpdate(MessaggiDTO msgDTO, FieldEnum[] fields) throws Exception {

	for (FieldEnum field : fields) {
	    this.updateFromDTOSelectively(msgDTO, field);
	}
    }

    /**
     * Costruisce un oggetto Messaggi a partire da un DTO aggiunge id_messaggio
     * 
     * @param MessaggiDTO
     *            dto
     * @return Messaggi
     * @throws DataIntegrationException
     */
    private Messaggi buildFromDTO(MessaggiDTO dto) throws DataIntegrationException {

	Messaggi messaggio = modelMapper().map(dto, Messaggi.class);
	// String publicMessageId = SecurityTools.generateMessageId(PUBLIC_ID_LENGTH);
	// messaggio.setIdMessaggio(publicMessageId);
	dataIntegration(messaggio);
	return messaggio;
    }

    private ModelMapper modelMapper() {

	return new ModelMapper();
    }

    private void dataIntegration(Messaggi entity) throws DataIntegrationException {

//	Optional<Servizi> servizioOpt = this.serviziRepository.findByIdcomuneAndCodicecomuneAndSoftware(entity.getIdcomune(),
//		entity.getCodicecomune(), entity.getSoftware());
	Optional<Servizi> servizioOpt = this.serviziRepository.findByIdServizio(entity.getIdentificativoServizio());
	// Verifica se il Servizio è presente
	if (!servizioOpt.isPresent()) {
	    throw new DataIntegrationException("Data Integration error: servizio con [identificativoServizio=" + entity.getIdentificativoServizio()+  "] non trovato.");
	}
	entity.setServizi(servizioOpt.get());
	// Set dello Stato Messaggio iniziale
	if (entity.getStatoMessaggio().getNome() == null) {
	    Optional<StatoMessaggio> statoMessaggioOpt = statoMessaggioRepository.findByNome(StatiMessaggioEnum.PRESO_IN_CARICO.name());
	    if (statoMessaggioOpt.isPresent()) {
		entity.setStatoMessaggio(statoMessaggioOpt.get());
	    }
	}
    }

    @Override
    public List<MessaggiDTO> findAllByStatoMessaggio(StatiMessaggioEnum stato) {

	List<MessaggiDTO> messaggiDTOs = null;
	Optional<StatoMessaggio> statoMessaggio = statoMessaggioRepository.findByNome(stato.name());
	if (statoMessaggio.isPresent()) {
	    List<Messaggi> messaggis = messaggiRepository.findByStatoMessaggio(statoMessaggio.get());
	    messaggiDTOs = ModelMapperUtils.mapListMessaggiToListMessaggiDTO(messaggis);
	}
	return messaggiDTOs;
    }
}

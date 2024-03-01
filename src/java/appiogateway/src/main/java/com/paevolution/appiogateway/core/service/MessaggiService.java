package com.paevolution.appiogateway.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.exceptions.DataIntegrationException;
import com.paevolution.appiogateway.utils.FieldEnum;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

public interface MessaggiService {

    Page<Messaggi> allPaginatedMessages(Pageable pageable);

    Optional<MessaggiDTO> findByMessageId(String idMessaggio);
    
    

    List<MessaggiDTO> findAllMessaggi() throws Exception;

    List<MessaggiDTO> findAllByStatoMessaggio(StatiMessaggioEnum stato);

    /**
     * 
     * metodo che effettua il salvataggio di un oggetto Messaggi a partire da un MessaggiDTO
     */
    // MessaggiDTO createMessaggi(MessaggiDTO messaggiDTO);
    /// MessaggiDTO createMessage(MessaggiDTO messaggiDTO);
    public MessaggiDTO createMessage(MessaggiDTO messaggiDTO) throws DataIntegrationException;

    void updateIdTransazione(Long id, String idTransazione);

    void updateFromDTOSelectively(MessaggiDTO msgDTO, FieldEnum field) throws Exception;

    void multiFieldUpdate(MessaggiDTO msgDTO, FieldEnum[] fields) throws Exception;
}

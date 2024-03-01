package com.paevolution.appiogateway.core.service;

import java.util.List;
import java.util.Optional;

import com.paevolution.appiogateway.core.domain.Servizi;
import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.dto.ServiziDTO;

public interface ServiziService {

    // Servizi findByIdcomuneCodicecomuneSoftware(String idcomune, String codicecomune, String software);

    Optional<Servizi> findByIdServizio(String idServizio);

    List<ServiziDTO> findAllServizi();

    List<ServiziDTO> findByTipoConnettore(TipoConnettore tipoConnettore);

    boolean isNotBlankOAuth2ClientInfo(String consumerKey, String consumerSecret, String clientRegistrationId);
}

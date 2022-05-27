package com.paevolution.appioproducer.core.domain.helper;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageToSendHelper implements Serializable {

    private static final long serialVersionUID = 2830197837312147363L;
    private String idcomune;
    private String codicecomune;
    private String software;
    private Integer codiceistanza;
    private Integer codicestato;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private String numeroistanza;
    private Date data;
    private String lavori;
    private String denominazioneServizio;
    private String urlPortaleServizi;
    private String destinatario;
    private Integer destinatarioCodiceanagrafe;
    private String destinatarioCodicefiscale;
    private Integer codicemovimento;
    private String movimento;
    ////private String messageId;
}

package com.paevolution.appiogateway.core.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiziDTO implements Serializable {

    private static final long serialVersionUID = -840813444601675150L;
    private Long id;
    private String idServizio;
    private String idcomune;
//    private String codicecomune;
//    private String software;
    private String nomeServizio;
    private String dipartimento;
    private String ente;
    private String codiceFiscaleEnte;
    private String chiavePrimaria;
    private String chiaveSecondaria;
    private String clientId;
    private String clientSecret;
    private String clientRegistrationId;
    private String tipoConnettore;
}

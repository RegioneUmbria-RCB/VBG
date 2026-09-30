package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileWsInType;

public interface RegistrazioniContabiliConverterService {

    RegistrazioneContabileType completaRegistrazioneContabile(RegistrazioneContabileWsInType regContabileWsIn);
}

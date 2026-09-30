package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;

public interface IProtocolloWSFactory {

    IProtocollazioneService createPort() throws Exception;
}

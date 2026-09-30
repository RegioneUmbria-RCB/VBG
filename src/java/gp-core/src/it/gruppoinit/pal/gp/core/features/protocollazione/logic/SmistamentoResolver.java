package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;

public class SmistamentoResolver {

    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService;

    public SmistamentoResolver(IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService) {

	this.vertProtoAttivoService = vertProtoAttivoService;
    }

    public String resolve() {

	return this.vertProtoAttivoService.getTipoSmistamentoDefault();
    }
}

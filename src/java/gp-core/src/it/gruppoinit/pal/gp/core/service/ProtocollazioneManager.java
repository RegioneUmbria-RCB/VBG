package it.gruppoinit.pal.gp.core.service;

import java.util.Map;

public interface ProtocollazioneManager {

    public Map<String, String> insertRipetiProtocollazioneFallitaIstanza(Integer codiceIstanza);

    public String insertNuovaRicevutaPratica(Integer codiceIstanza);
}

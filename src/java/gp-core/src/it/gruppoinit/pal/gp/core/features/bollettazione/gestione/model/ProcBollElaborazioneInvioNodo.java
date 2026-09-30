package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneBase;

public class ProcBollElaborazioneInvioNodo {

    public static Map<String, ProcBollElaborazioneInvioNodoBean> elaborazioniInCorso = new HashMap<String, ProcBollElaborazioneInvioNodoBean>();

    public static ProcBollElaborazioneInvioNodoBean getStatoElaborazione(int idTestataBollettazione) {

	String key = new ProceduraInvioNodoBollettazioneBase().getKeyBollettazioneInvioNodoString(idTestataBollettazione);
	return elaborazioniInCorso.get(key);
    }

    public static Set<Integer> getElaborazioniInCorso(Integer[] idBollettazioneTestata) {

	Set<Integer> ret = new HashSet<Integer>();
	for (Integer id : idBollettazioneTestata) {
	    String key = new ProceduraInvioNodoBollettazioneBase().getKeyBollettazioneInvioNodoString(id);
	    if (elaborazioniInCorso.containsKey(key)) {
		ret.add(id);
	    }
	}
	return ret;
    }

    public static void rimuovi(Integer idBollettazioneTestata) {

	String key = new ProceduraInvioNodoBollettazioneBase().getKeyBollettazioneInvioNodoString(idBollettazioneTestata);
	elaborazioniInCorso.remove(key);
    }
}

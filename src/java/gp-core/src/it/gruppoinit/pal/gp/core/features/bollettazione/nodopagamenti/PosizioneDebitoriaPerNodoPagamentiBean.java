package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.List;
import java.util.Set;

public class PosizioneDebitoriaPerNodoPagamentiBean {

    private List<PosizioneDebitoriaBollettazioneBean> posizioni;
    private Set<String> codiciComune;

    public PosizioneDebitoriaPerNodoPagamentiBean(List<PosizioneDebitoriaBollettazioneBean> posizioni, Set<String> codiciComune) {

	this.posizioni = posizioni;
	this.codiciComune = codiciComune;
    }

    public List<PosizioneDebitoriaBollettazioneBean> getPosizioni() {

	return posizioni;
    }

    public Set<String> getCodiciComune() {

	return codiciComune;
    }
}

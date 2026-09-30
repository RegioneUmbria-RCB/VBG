package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.AnagrafeTributariaDAO;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErrori;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserGruppi;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribRiferimentiIstanze;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnagrafeTribParserEsito;

public class EdiliziaTracciatoATParser extends BaseATParser {

    @Autowired
    private AnagrafeTributariaDAO anagrafeTributariaDAO;

    public EdiliziaTracciatoATParser(AnagrafeTributariaDAO anagrafeTributariaDAO) {

	this.anagrafeTributariaDAO = anagrafeTributariaDAO;
    }

    @Override
    public AnagrafeTribParserEsito parseInternal(AnagrafeTribParserEsito esito) {

	// nel l'edilizia ci sono 5 tipi di record tracciato differenti
	// devo cercare di associare più righe ad un gruppo
	List<AnTribParserGruppi> gruppi = esito.getGruppi();
	List<AnTribParserGruppi> gruppiRidefinito = new ArrayList<AnTribParserGruppi>();
	Map<String, AnTribParserGruppi> mGs = new HashMap<String, AnTribParserGruppi>();
	for (AnTribParserGruppi g : gruppi) {
	    IATParserProvvedimento datiProvvedimento = null;
	    List<AnTribParserErrori> e = g.getErrori();
	    datiProvvedimento = ATParserDatiProvvedimentoEdilizia.fromRigaTraccato(e.get(0).getRigheTracciato().get(0).getRigaTracciato());
	    String chiave = datiProvvedimento.getHashChiaveProvvedimento();
	    AnTribParserGruppi gruppoPresente = mGs.get(chiave);
	    if (gruppoPresente == null) {
		mGs.put(chiave, g);
		gruppiRidefinito.add(g);
		associaIstanze(g, datiProvvedimento);
	    } else {
		gruppoPresente.getErrori().addAll(g.getErrori());
	    }
	}
	esito.getGruppi().clear();
	esito.getGruppi().addAll(gruppiRidefinito);
	return esito;
    }

    private void associaIstanze(AnTribParserGruppi g, IATParserProvvedimento datiProvvedimento) {

	if (datiProvvedimento.isDatiRicercabili()) {
	    List<Integer> istanzeTrovate = anagrafeTributariaDAO.cercaIstanze(datiProvvedimento);
	    for (Integer codiceIstanza : istanzeTrovate) {
		g.getIstanzeTrovate().add(new AnTribRiferimentiIstanze(codiceIstanza));
	    }
	}
    }

    @Override
    public ATTipologiaTracciatoEnum getTipologia() {

	return ATTipologiaTracciatoEnum.EDILIZIA;
    }
}

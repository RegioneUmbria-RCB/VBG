package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.AnagrafeTributariaDAO;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErrori;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErroriTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserGruppi;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribRiferimentiIstanze;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnagrafeTribParserEsito;

public class CommercioTracciatoATParser extends BaseATParser implements ITracciatoATParser {

    private AnagrafeTributariaDAO anagrafeTributariaDAO;

    public CommercioTracciatoATParser(AnagrafeTributariaDAO anagrafeTributariaDAO) {

	this.anagrafeTributariaDAO = anagrafeTributariaDAO;
    }

    @Override
    public AnagrafeTribParserEsito parseInternal(AnagrafeTribParserEsito esito) {

	// nel commercio ogni riga di tracciato corrisponde ad una istanza
	// overo esistono solamente record di tipo 1
	// devo cercare di associare una o più istanza alla riga del gruppo
	for (AnTribParserGruppi g : esito.getGruppi()) {
	    IATParserProvvedimento datiProvvedimento = null;
	    List<AnTribParserErrori> errori = g.getErrori();
	    for (AnTribParserErrori e : errori) {
		// per il commercio esistono solamente righe di tipo 1
		AnTribParserErroriTracciato riga = e.getRigheTracciato().get(0);
		datiProvvedimento = ATParserDatiProvvedimentoCommercio.fromRigaTraccato(riga.getRigaTracciato());
		break;
	    }
	    associaIstanze(g, datiProvvedimento);
	}
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

	return ATTipologiaTracciatoEnum.COMMERCIO;
    }
}

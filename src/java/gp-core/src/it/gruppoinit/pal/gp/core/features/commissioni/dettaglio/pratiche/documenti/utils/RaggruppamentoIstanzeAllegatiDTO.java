package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;

public class RaggruppamentoIstanzeAllegatiDTO implements IFunzioneRaggruppamentoDocumenti<IstanzeallegatiDTO> {

    private final static String CATEGORIA = "documentiEndo";

    public RaggruppamentoIstanzeAllegatiDTO() {

    }

    public Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getGroup(List<IstanzeallegatiDTO> values) {

	HashMap<String, CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> map = new HashMap<String, CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti>();
	for (IstanzeallegatiDTO istAll : values) {
	    Integer fkId = istAll.getId().getCodice();
	    String codice = istAll.getProcedimento(); // TODO: non c'è niente di meglio?
	    String titolo = istAll.getProcedimento();
	    String nome = istAll.getAllegatoextra();
	    Integer codiceOggetto = istAll.getCodiceOggetto();
	    String nomeFile = istAll.getNomeFile();
	    CommissioniDettaglioDocumentiPratica.RiferimentiDocumento rifDoc = new CommissioniDettaglioDocumentiPratica.RiferimentiDocumento(fkId,
		    nome, nomeFile, codiceOggetto);
	    if (!map.containsKey(codice)) {
		CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti docs = new CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti(
			CATEGORIA, titolo);
		map.put(codice, docs);
	    }
	    map.get(codice).aggiungiDocumento(rifDoc);
	}
	return map.values();
    }
}

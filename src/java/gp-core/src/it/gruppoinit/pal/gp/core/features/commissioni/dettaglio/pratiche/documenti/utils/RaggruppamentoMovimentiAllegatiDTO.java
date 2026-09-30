package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;

public class RaggruppamentoMovimentiAllegatiDTO implements IFunzioneRaggruppamentoDocumenti<MovimentiallegatiDTO> {

    private final static String CATEGORIA = "documentiMovimenti";

    public RaggruppamentoMovimentiAllegatiDTO() {

    }

    public Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getGroup(List<MovimentiallegatiDTO> values) {

	HashMap<String, CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> map = new HashMap<String, CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti>();
	for (MovimentiallegatiDTO movAll : values) {
	    Integer fkId = movAll.getId().getCodice();
	    String codice = movAll.getTipomovimento();
	    String titolo = movAll.getDescrizioneMovimento();
	    String nome = movAll.getDescrizione();
	    Integer codiceOggetto = movAll.getCodiceOggetto();
	    String nomeFile = movAll.getNomeFile();
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

package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.ArrayList;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ListaoneriSoloImp1 extends SegnapostoTestualeBaseConValoreMultiplo {

    @Override
    public String getNome() {

	return "LISTAONERISOLOIMP1";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

    }

    @Override
    protected String[] onGetValori(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	ArrayList<String> listaOneri = new ArrayList<String>();
	for (Istanzeoneri io : data.getIstanza().getIstanzeoneris()) {
	    StringBuffer sb = new StringBuffer();
	    if (io.getTipicausalioneri() != null) {
		if (io.getTipicausalioneri().getRaggruppamentocausalioneri() != null) {
		    sb.append(io.getTipicausalioneri().getRaggruppamentocausalioneri().getRcoDescr()).append("\t");
		}
		if (io.getTipicausalioneri().getCoDescrizione() != null) {
		    sb.append("(").append(io.getTipicausalioneri().getCoDescrizione()).append(")\t");
		}
	    }
	    sb.append(getEntrataUscita(io.getFlentratauscita())).append("\t");
	    if (io.getInventarioprocedimenti() != null) {
		sb.append(io.getInventarioprocedimenti().getProcedimento()).append("\t");
	    }
	    if (io.getAmministrazioni() != null) {
		sb.append(io.getAmministrazioni().getAmministrazione()).append("\t");
	    }
	    sb.append(Utilities.formatDate(io.getDatascadenza(), WebConstants.DATE_FORMAT_PATTERN)).append("\t");
	    sb.append(calcolaImporto(io)).append("\t");
	    if (io.getDatapagamento() != null) {
		sb.append("Pag. il ").append(Utilities.formatDate(io.getDatapagamento(), WebConstants.DATE_FORMAT_PATTERN)).append("\t");
	    }
	    if (io.getDocriferimento() != null) {
		sb.append(io.getDocriferimento()).append("\t");
	    }
	    if (io.getTipimodalitapagamento() != null) {
		sb.append(io.getTipimodalitapagamento().getMpDescrestesa());
	    }
	    listaOneri.add(sb.toString());
	}
	return listaOneri.toArray(new String[0]);
    }

    private String getEntrataUscita(Boolean flentratauscita) {

	return Boolean.TRUE.equals(flentratauscita) ? "Entrata" : "Uscita";
    }

    private String calcolaImporto(Istanzeoneri io) {

	Integer prezzo = Integer.valueOf(io.getPrezzo().intValue());
	Integer prezzoRibasso = null;
	Integer prezzoUscita = 0;
	if (io.getFlribasso()) {
	    prezzoRibasso = prezzo - (prezzo / 100 * (100 - io.getPercribasso()));
	    prezzoUscita = prezzo / 100 * (100 - io.getPercribasso());
	} else {
	    prezzoUscita = prezzo;
	}
	return prezzoRibasso != null ? "Euro " + prezzoUscita + " ribasso di " + prezzoRibasso : "Euro " + prezzoUscita;
    }
}

package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.DatiRichiedente;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;

public class CointestatarioACapoEsteso extends SegnapostoTestualeBaseConValoreMultiplo {

    private final Logger log = LoggerFactory.getLogger(CointestatarioACapoEsteso.class);

    @Override
    public String getNome() {

	return "COINTESTATARIACAPOESTESO";
    }

    @Override
    public boolean haArgomenti() {

	return true;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	// TODO Auto-generated method stub
    }

    @Override
    protected String[] onGetValori(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	ArrayList<String> listaRichiedenti = new ArrayList<String>();
	List<DatiRichiedente> drList = parseCointestatariArguments(argomenti, data);
	for (DatiRichiedente dr : drList) {
	    listaRichiedenti.add(
		    dr.buildCointestatarioEstesoString(true, true, true, "", false, true, TipoFileEnum.getRitornoACapo(getTipoFile())).toString());
	}
	return listaRichiedenti.toArray(new String[0]);
    }

    private List<DatiRichiedente> parseCointestatariArguments(String[] arguments, IUsefulDataForPlaceholderReplacement data) {

	List<DatiRichiedente> retData = new ArrayList<DatiRichiedente>();
	if (arguments.length == 0) {
	    return retData;
	}
	for (int i = 0; i < arguments.length; i++) {
	    String argomento = arguments[i];
	    if (argomento.equals("R")) {
		retData.add(new DatiRichiedente(data.getIstanza().getRichiedente(), "Richiedente"));
		continue;
	    }
	    if (argomento.equals("T")) {
		Anagrafe a = data.getIstanza().getProfessionista();
		if (a != null) {
		    retData.add(new DatiRichiedente(a, "Tecnico"));
		}
		continue;
	    }
	    if (argomento.equals("A")) {
		Anagrafe a = data.getIstanza().getTitolarelegale();
		if (a != null) {
		    retData.add(new DatiRichiedente(a, "Titolare legale"));
		}
		continue;
	    }
	    try {
		Integer codTipoSogg = null;
		if (argomento.length() > 0) {
		    codTipoSogg = Integer.parseInt(argomento);
		}
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    retData.add(new DatiRichiedente(ir));
		}
	    } catch (NumberFormatException e) {
		log.error(
			"parseCointestatariArguments() - il valore {} non può essere interpretato come un codice tipo soggetto perchè non è numerico",
			new Object[] { argomento });
	    }
	}
	return retData;
    }
}

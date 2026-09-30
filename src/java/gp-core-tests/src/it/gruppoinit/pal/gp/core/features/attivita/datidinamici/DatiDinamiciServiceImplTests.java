package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class DatiDinamiciServiceImplTests {

    @Test
    public void recuperaCampiInDataConIndiceMolteplicitaDifferenteTornaLaListaCorretta() {

	DatiDinamiciServiceImpl service = new DatiDinamiciServiceImpl();
	Calendar dataDiRiferimento = Calendar.getInstance();
	dataDiRiferimento.set(Calendar.DAY_OF_MONTH, 18);
	dataDiRiferimento.set(Calendar.MONTH, 4);
	dataDiRiferimento.set(Calendar.YEAR, 2021);
	dataDiRiferimento.set(Calendar.HOUR, 0);
	dataDiRiferimento.set(Calendar.MINUTE, 0);
	dataDiRiferimento.set(Calendar.SECOND, 0);
	dataDiRiferimento.set(Calendar.MILLISECOND, 0);
	//
	List<Integer> idCampi = new ArrayList<Integer>();
	idCampi.add(400);
	idCampi.add(401);
	//
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> elencoCompleto = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(
		new ChiaveCampoDinamicoPerDataComparator(false));
	elencoCompleto.put(this.getChiave(18, 4, 2021, 401, 0), new ValoreCampoPresente("ABBIGLIAMENTO", "ABBIGLIAMENTO"));
	elencoCompleto.put(this.getChiave(18, 4, 2021, 401, 1), new ValoreCampoPresente("SCARPE", "SCARPE"));
	elencoCompleto.put(this.getChiave(18, 4, 2021, 401, 2), new ValoreCampoPresente("CAPPELLI", "CAPPELLI"));
	//
	elencoCompleto.put(this.getChiave(2, 3, 2021, 401, 0), new ValoreCampoPresente("MANGIME PER CANI", "MANGIME PER CANI"));
	elencoCompleto.put(this.getChiave(2, 3, 2021, 401, 1), new ValoreCampoPresente("MANGIME PER UCCELLI", "MANGIME PER UCCELLI"));
	//
	elencoCompleto.put(this.getChiave(14, 2, 2021, 400, 0), new ValoreCampoPresente("50 ( MQ DEL NEGOZIO )", "50 ( MQ DEL NEGOZIO )"));
	//
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> elencoGiaPresenti = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	//
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = service.recuperaCampiInData(dataDiRiferimento.getTime(), idCampi, elencoCompleto,
		elencoGiaPresenti);
	//
	assertEquals("Erano attesi 4 elementi in mappa, ne sono presenti " + mappa.entrySet().size(), 4, mappa.entrySet().size());
    }

    private ChiaveCampoDinamicoPerData getChiave(int dayOfMonth, int month, int year, int idCampo, int indiceMolteplicita) {

	Calendar data1 = Calendar.getInstance();
	data1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	data1.set(Calendar.MONTH, month);
	data1.set(Calendar.YEAR, year);
	data1.set(Calendar.HOUR, indiceMolteplicita);
	data1.set(Calendar.HOUR, 0);
	data1.set(Calendar.MINUTE, 0);
	data1.set(Calendar.SECOND, 0);
	data1.set(Calendar.MILLISECOND, 0);
	return new ChiaveCampoDinamicoPerData(data1.getTime(), idCampo, 0, indiceMolteplicita);
    }
}

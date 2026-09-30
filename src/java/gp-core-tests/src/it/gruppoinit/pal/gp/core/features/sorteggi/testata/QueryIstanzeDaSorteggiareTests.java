package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;

public class QueryIstanzeDaSorteggiareTests {

    @Test
    public void QueryDaMovimentiConTuttiIFiltriImpostatiTest() {

	FiltriSorteggioBean filtro = new FiltriSorteggioBean();
	filtro.setCodiceComune("D612");
	filtro.setCodiciStatoIstanza(Arrays.asList("AT", "CP"));
	filtro.setDataAl(new Date());
	filtro.setDataDal(new Date());
	filtro.setEscludiIstanzeSorteggiate(true);
	filtro.setIdCategorieDaEscludere(Arrays.asList(1, 2));
	filtro.setIdEndoprocedimenti(Arrays.asList(10, 20));
	filtro.setIdNatureEndo(Arrays.asList(100, 200));
	filtro.setIdProcedura(Arrays.asList(1000, 2000));
	filtro.setIdSorteggiDaEscludere(Arrays.asList(10000, 20000));
	filtro.setIdTipiArchivioIstanza(5);
	filtro.setModalitaSelezioneEndo("OR");
	filtro.setScCodiciInterventoProc(Arrays.asList("01", "02"));
	filtro.setSoftware("CF");
	filtro.setTipoMovimento("CO1001");
	filtro.setTipoRicercaMovimento(2);
	QueryIstanzeDaSorteggiare query = new QueryIstanzeDaSorteggiare(null, filtro);
	String sqlQuery = query.buildQuery();
	char c = '?';
	int count = 0;
	for (char ch : sqlQuery.toCharArray()) {
	    if (ch == c) {
		count++;
	    }
	}
	assertEquals(count, 24); //22 impostati + idcomune + flag escludi interventi no sorteggio
	assertEquals(query.getParameters().size(), count);
    }
}

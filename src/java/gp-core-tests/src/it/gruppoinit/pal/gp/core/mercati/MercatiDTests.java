package it.gruppoinit.pal.gp.core.mercati;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDServiceImpl;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

public class MercatiDTests {

    @Test(expected = IllegalArgumentException.class)
    public void conteggioDeiPosteggiPerMercato_rilanciaEccezioneSeCodiceMercatoNUllo() {

	MercatiDServiceImpl service = new MercatiDServiceImpl();
	service.setMercatiDDAO(new BaseMercatiDFakeAdapter() {

	    @Override
	    public int countRecord(FilterTable filterTable) {

		return super.countRecord(filterTable);
	    }

	    @Override
	    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

		// TODO Auto-generated method stub
		return 0;
	    }
	});
	service.countByMercato(null, null);
    }

    @Test
    public void conteggioDeiPosteggiPerMercato_TornaUnoSeEnumNulla() {

	// torna uno perché viene impostata a ALL di default e quindi imposto solo il filtro percodicemercato
	MercatiDServiceImpl service = new MercatiDServiceImpl();
	service.setMercatiDDAO(new BaseMercatiDFakeAdapter() {

	    @Override
	    public int countRecord(FilterTable ft) {

		Set<FilterRestriction> restrictions = ft.getRestrictions();
		int numFields = 0;
		for (FilterRestriction fr : restrictions) {
		    if (fr.getFilterFields() != null) {
			numFields += fr.getFilterFields().size();
		    }
		}
		return numFields;
	    }

	    @Override
	    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

		// TODO Auto-generated method stub
		return 0;
	    }
	});
	int countByMercato = service.countByMercato(3, null);
	Assert.assertTrue(countByMercato == 1);
    }

    @Test
    public void conteggioDeiPosteggiPerMercato_TornaListadiUnoSeEnumALL() {

	MercatiDServiceImpl service = new MercatiDServiceImpl();
	service.setMercatiDDAO(new BaseMercatiDFakeAdapter() {

	    @Override
	    public int countRecord(FilterTable ft) {

		Set<FilterRestriction> restrictions = ft.getRestrictions();
		int numFields = 0;
		for (FilterRestriction fr : restrictions) {
		    if (fr.getFilterFields() != null) {
			numFields += fr.getFilterFields().size();
		    }
		}
		return numFields;
	    }

	    @Override
	    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

		// TODO Auto-generated method stub
		return 0;
	    }
	});
	int countByMercato = service.countByMercato(3, PosteggiEnum.ALL);
	Assert.assertTrue(countByMercato == 1);
    }

    @Test
    public void conteggioDeiPosteggiPerMercato_TornaListadiTreSeEnumACTIVE() {

	MercatiDServiceImpl service = new MercatiDServiceImpl();
	service.setMercatiDDAO(new BaseMercatiDFakeAdapter() {

	    @Override
	    public int countRecord(FilterTable ft) {

		Set<FilterRestriction> restrictions = ft.getRestrictions();
		int numFields = 0;
		for (FilterRestriction fr : restrictions) {
		    if (fr.getFilterFields() != null) {
			numFields += fr.getFilterFields().size();
		    }
		}
		return numFields;
	    }

	    @Override
	    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

		// TODO Auto-generated method stub
		return 0;
	    }
	});
	int countByMercato = service.countByMercato(3, PosteggiEnum.ACTIVE);
	Assert.assertTrue(countByMercato == 3);
    }

    @Test
    public void conteggioDeiPosteggiPerMercato_TornaListadiDUESeEnumDISABLED() {

	MercatiDServiceImpl service = new MercatiDServiceImpl();
	service.setMercatiDDAO(new BaseMercatiDFakeAdapter() {

	    @Override
	    public int countRecord(FilterTable ft) {

		Set<FilterRestriction> restrictions = ft.getRestrictions();
		int numFields = 0;
		for (FilterRestriction fr : restrictions) {
		    if (fr.getFilterFields() != null) {
			numFields += fr.getFilterFields().size();
		    }
		}
		return numFields;
	    }

	    @Override
	    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

		// TODO Auto-generated method stub
		return 0;
	    }
	});
	int countByMercato = service.countByMercato(3, PosteggiEnum.DISABLED);
	Assert.assertTrue(countByMercato == 2);
    }
}

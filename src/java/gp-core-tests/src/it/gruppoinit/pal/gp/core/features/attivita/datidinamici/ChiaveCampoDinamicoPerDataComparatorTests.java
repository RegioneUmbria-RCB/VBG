package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

public class ChiaveCampoDinamicoPerDataComparatorTests {

    private Date dataSenzaOre(int giorno, int mese, int anno) {

	Calendar data1 = Calendar.getInstance();
	data1.set(Calendar.DAY_OF_MONTH, giorno);
	data1.set(Calendar.MONTH, mese);
	data1.set(Calendar.YEAR, anno);
	data1.set(Calendar.HOUR, 0);
	data1.set(Calendar.MINUTE, 0);
	data1.set(Calendar.SECOND, 0);
	data1.set(Calendar.MILLISECOND, 0);
	return data1.getTime();
    }

    @Test
    public void AggiuntiElementiInOrdineDecrescenteRestituisceMappaInOrdineDecrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(false);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Letizia invece non è lei", "Mendichi Letizia", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Alice invece non è lei", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è lei", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Stefano 2 invece non è lui", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Stefano invece non è lui", "Mendichi Stefano", entryArray[idx].getValue().getValore());
    }

    @Test
    public void AggiuntiElementiInOrdineCrescenteRestituisceMappaInOrdineDecrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(false);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Letizia invece non è lei", "Mendichi Letizia", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Alice invece non è lei", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è lei", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Stefano 2 invece non è lui", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Stefano invece non è lui", "Mendichi Stefano", entryArray[idx].getValue().getValore());
    }

    @Test
    public void AggiuntiElementiSenzaOrdineRestituisceMappaInOrdineDecrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(false);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Letizia invece non è lei", "Mendichi Letizia", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Alice invece non è lei", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è lei", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Stefano 2 invece non è lui", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Stefano invece non è lui", "Mendichi Stefano", entryArray[idx].getValue().getValore());
    }

    @Test
    public void AggiuntiElementiInOrdineDecrescenteRestituisceMappaInOrdineCrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(true);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Stefano invece non è", "Mendichi Stefano", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Stefano 2 invece non è", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Alice invece non è", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Letizia invece non è", "Mendichi Letizia", entryArray[idx].getValue().getValore());
    }

    @Test
    public void AggiuntiElementiInOrdineCrescenteRestituisceMappaInOrdineCrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(true);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Stefano invece non è", "Mendichi Stefano", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Stefano 2 invece non è", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Alice invece non è", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Letizia invece non è", "Mendichi Letizia", entryArray[idx].getValue().getValore());
    }

    @Test
    public void AggiuntiElementiSenzaOrdineRestituisceMappaInOrdineCrescente() {

	ChiaveCampoDinamicoPerDataComparator comparator = new ChiaveCampoDinamicoPerDataComparator(true);
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(comparator);
	Date data3 = this.dataSenzaOre(12, 8, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data3, 311, 0, 0), new ValoreCampoPresente("Massetti Chiara", "Massetti Chiara"));
	Date data5 = this.dataSenzaOre(26, 0, 2015);
	mappa.put(new ChiaveCampoDinamicoPerData(data5, 311, 0, 0), new ValoreCampoPresente("Mendichi Letizia", "Mendichi Letizia"));
	Date data2 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data2, 311, 0, 1), new ValoreCampoPresente("Mendichi Stefano 2", "Mendichi Stefano 2"));
	Date data4 = this.dataSenzaOre(21, 1, 2013);
	mappa.put(new ChiaveCampoDinamicoPerData(data4, 311, 0, 0), new ValoreCampoPresente("Mendichi Alice", "Mendichi Alice"));
	Date data1 = this.dataSenzaOre(26, 6, 1983);
	mappa.put(new ChiaveCampoDinamicoPerData(data1, 311, 0, 0), new ValoreCampoPresente("Mendichi Stefano", "Mendichi Stefano"));
	Set<Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>> entries = mappa.entrySet();
	Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
	int idx = 0;
	assertEquals("Il primo deve essere Mendichi Stefano invece non è", "Mendichi Stefano", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il secondo deve essere Mendichi Stefano 2 invece non è", "Mendichi Stefano 2", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il terzo deve essere Massetti Chiara invece non è", "Massetti Chiara", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quarto deve essere Mendichi Alice invece non è", "Mendichi Alice", entryArray[idx].getValue().getValore());
	idx++;
	assertEquals("Il quinto deve essere Mendichi Letizia invece non è", "Mendichi Letizia", entryArray[idx].getValue().getValore());
    }
}

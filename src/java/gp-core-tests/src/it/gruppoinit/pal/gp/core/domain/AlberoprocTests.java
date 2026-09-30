package it.gruppoinit.pal.gp.core.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AlberoprocTests {

    @Test
    public void getPercorsoScCodiceValorizzato() {

	Alberoproc albero = new Alberoproc();
	albero.setScCodice("01020106");
	List<String> elenco = albero.getPercorsoScCodice();
	Assert.assertEquals("Deve avere 4 elementi", 4, elenco.size());
	Assert.assertEquals("Primo elemento uguale a 01", "01", elenco.get(0));
	Assert.assertEquals("Secondo elemento uguale a 0102", "0102", elenco.get(1));
	Assert.assertEquals("Terzo elemento uguale a 010201", "010201", elenco.get(2));
	Assert.assertEquals("Quarto elemento uguale a 01020106", "01020106", elenco.get(3));
    }

    @Test
    public void getPercorsoScCodiceNull() {

	Alberoproc albero = new Alberoproc();
	List<String> elenco = albero.getPercorsoScCodice();
	Assert.assertNull("Deve essere un elenco null", elenco);
    }
}

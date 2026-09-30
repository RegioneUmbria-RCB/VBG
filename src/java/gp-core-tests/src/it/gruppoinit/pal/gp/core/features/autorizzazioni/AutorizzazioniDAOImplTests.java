package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class AutorizzazioniDAOImplTests {

    @Test
    public void calcolaDataRegistrazione_restituisce_primo_estremo() {

	String inter = "01/01-30/06";
	Calendar dataM = Calendar.getInstance();
	dataM.set(Calendar.YEAR, dataM.get(Calendar.YEAR));
	dataM.set(Calendar.MONTH, 11);
	dataM.set(Calendar.DAY_OF_MONTH, 12);
	dataM.set(Calendar.HOUR_OF_DAY, 0);
	dataM.set(Calendar.MINUTE, 0);
	dataM.set(Calendar.SECOND, 0);
	dataM.set(Calendar.MILLISECOND, 0);
	Date _dataM = dataM.getTime();
	AutorizzazioniDAOImpl ad = new AutorizzazioniDAOImpl();
	Date calcolaDataRegistrazione = ad.calcolaDataRegistrazione(inter, _dataM);
	Calendar expected = Calendar.getInstance();
	expected.set(Calendar.YEAR, expected.get(Calendar.YEAR));
	expected.set(Calendar.MONTH, 5);
	expected.set(Calendar.DAY_OF_MONTH, 30);
	expected.set(Calendar.HOUR_OF_DAY, 0);
	expected.set(Calendar.MINUTE, 0);
	expected.set(Calendar.SECOND, 0);
	expected.set(Calendar.MILLISECOND, 0);
	Date exp = expected.getTime();
	Assert.assertEquals("Deve restituire 30/06/2022", exp, calcolaDataRegistrazione);
    }

    @Test
    public void calcolaDataRegistrazione_restituisce_secondo_estremo_1() {

	String inter = "01/01-30/06";
	Calendar dataM = Calendar.getInstance();
	//dataM.set(Calendar.YEAR, 2022);
	dataM.set(Calendar.MONTH, 7);
	dataM.set(Calendar.DAY_OF_MONTH, 27);
	dataM.set(Calendar.HOUR_OF_DAY, 0);
	dataM.set(Calendar.MINUTE, 0);
	dataM.set(Calendar.SECOND, 0);
	dataM.set(Calendar.MILLISECOND, 0);
	Date _dataM = dataM.getTime();
	AutorizzazioniDAOImpl ad = new AutorizzazioniDAOImpl();
	Date calcolaDataRegistrazione = ad.calcolaDataRegistrazione(inter, _dataM);
	Calendar expected = Calendar.getInstance();
	//expected.set(Calendar.YEAR, 2022);
	expected.set(Calendar.MONTH, 5);
	expected.set(Calendar.DAY_OF_MONTH, 30);
	expected.set(Calendar.HOUR_OF_DAY, 0);
	expected.set(Calendar.MINUTE, 0);
	expected.set(Calendar.SECOND, 0);
	expected.set(Calendar.MILLISECOND, 0);
	Date exp = expected.getTime();
	Assert.assertEquals("Deve restituire 30/06/2022", exp, calcolaDataRegistrazione);
    }

    @Test
    public void calcolaDataRegistrazione_restituisce_secondo_estremo_2() {

	String inter = "30/06-31/12";
	Calendar dataM = Calendar.getInstance();
	dataM.set(Calendar.YEAR, 2022);
	dataM.set(Calendar.MONTH, 3);
	dataM.set(Calendar.DAY_OF_MONTH, 27);
	dataM.set(Calendar.HOUR_OF_DAY, 0);
	dataM.set(Calendar.MINUTE, 0);
	dataM.set(Calendar.SECOND, 0);
	dataM.set(Calendar.MILLISECOND, 0);
	Date _dataM = dataM.getTime();
	AutorizzazioniDAOImpl ad = new AutorizzazioniDAOImpl();
	Date calcolaDataRegistrazione = ad.calcolaDataRegistrazione(inter, _dataM);
	Calendar expected = Calendar.getInstance();
	expected.set(Calendar.YEAR, expected.get(Calendar.YEAR) - 1);
	expected.set(Calendar.MONTH, 11);
	expected.set(Calendar.DAY_OF_MONTH, 31);
	expected.set(Calendar.HOUR_OF_DAY, 0);
	expected.set(Calendar.MINUTE, 0);
	expected.set(Calendar.SECOND, 0);
	expected.set(Calendar.MILLISECOND, 0);
	Date exp = expected.getTime();
	Assert.assertEquals("Deve restituire 31/12/2021", exp, calcolaDataRegistrazione);
    }
}

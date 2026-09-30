package it.gruppoinit.pal.gp.core.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class SecureIdUtilsTests {

    @Test()
    public void codificaIdConDataTornaHashCodeCorretto() {

	Integer id = 5000;
	String publicKey = "26/07/1983";
	String retVal1 = SecureIdUtils.encode(id, publicKey, "E230");
	Calendar c = new GregorianCalendar();
	c.set(1983, 6, 26);
	String publicKey2 = Utilities.formatDate(c.getTime(), false);
	String retVal2 = SecureIdUtils.encode(id, publicKey2, "E230");
	Assert.assertEquals("Devono essere identici", retVal1, retVal2);
    }

    @Test()
    public void codificaIdTornaHashAspettato() {

	Integer id = 5000;
	String publicKey = "26/07/1983";
	String retVal1 = SecureIdUtils.encode(id, publicKey, "E230");
	String retVal2 = "5000.E230.-144669317";
	Assert.assertEquals("Devono essere identici", retVal1, retVal2);
    }
}

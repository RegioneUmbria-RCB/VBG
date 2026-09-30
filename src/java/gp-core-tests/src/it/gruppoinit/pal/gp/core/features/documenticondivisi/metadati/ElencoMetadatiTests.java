package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ElencoMetadatiTests {

    @Test
    public void serializza_elenco_con_elementi_non_genera_errori() {

	DocumentiCondivisiMetadato dcm1 = new DocumentiCondivisiMetadato("codice-istanza", 6568);
	DocumentiCondivisiMetadato dcm2 = new DocumentiCondivisiMetadato("numero-istanza", "123/2020");
	List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>(0);
	metadati.add(dcm1);
	metadati.add(dcm2);
	ElencoMetadati elenco = new ElencoMetadati(metadati);
	String actual = elenco.toXmlString();
	String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><metadati_file><elenco_metadati><codice_istanza>6568</codice_istanza><numero_istanza>123/2020</numero_istanza></elenco_metadati></metadati_file>";
	Assert.assertEquals("La stringa XML è stata generata correttamente! ", expected, actual);
    }

    @Test
    public void serializza_elenco_contiene_dichiarazione_xml() {

	List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>(0);
	ElencoMetadati elenco = new ElencoMetadati(metadati);
	String actual = elenco.toXmlString();
	String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><metadati_file><elenco_metadati/></metadati_file>";
	Assert.assertEquals("contiene dichiarazione xml", expected, actual);
    }

    @Test
    public void serializza_elenco_effettua_escape_dei_caratteri_non_ammessi_in_xml() {

	DocumentiCondivisiMetadato dcm1 = new DocumentiCondivisiMetadato("escape", "<>&");
	List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>(0);
	metadati.add(dcm1);
	ElencoMetadati elenco = new ElencoMetadati(metadati);
	String actual = elenco.toXmlString();
	String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><metadati_file><elenco_metadati><escape>&lt;&gt;&amp;</escape></elenco_metadati></metadati_file>";
	Assert.assertEquals("I caratteri non ammessi vengono escapati", expected, actual);
    }
}

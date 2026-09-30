package it.gruppoinit.pal.gp.core.features.segnaposto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TipoFileEnumTests {

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreNonConosciutoVaInErrore() {

	TipoFileEnum.fromValue("smendy");
    }

    @Test
    public void enumeratoreDaStringaOdtRitornaEnumeratoreODT() {

	boolean esito = TipoFileEnum.ODT.equals(TipoFileEnum.fromValue("oDt"));
	assertTrue("L'estensione odt non è stata risolta come TipoFileEnum.ODT", esito);
    }

    @Test
    public void enumeratoreDaStringaRtfRitornaEnumeratoreRTF() {

	boolean esito = TipoFileEnum.RTF.equals(TipoFileEnum.fromValue("rTf"));
	assertTrue("L'esensione rtf non è stata risolta come TipoFileEnum.RTF", esito);
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileVuotoVaInErrore() {

	TipoFileEnum.fromFileName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileNullVaInErrore() {

	TipoFileEnum.fromFileName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileSenzaEstensioneVaInErrore() {

	TipoFileEnum.fromFileName("smendy");
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileConEstensioneSconosciutaVaInErrore() {

	TipoFileEnum.fromFileName("smendy.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileConEstensioneRTFRitornaEnumeratoreRTF() {

	boolean esito = TipoFileEnum.RTF.equals(TipoFileEnum.fromFileName("smendy.rtf"));
	assertTrue("L'esensione rtf non è stata risolta come TipoFileEnum.RTF", esito);
    }

    @Test(expected = IllegalArgumentException.class)
    public void enumeratoreDaNomeFileConEstensioneODTRitornaEnumeratoreODT() {

	boolean esito = TipoFileEnum.RTF.equals(TipoFileEnum.fromFileName("smendy.Odt"));
	assertTrue("L'esensione odt non è stata risolta come TipoFileEnum.ODT", esito);
    }
}

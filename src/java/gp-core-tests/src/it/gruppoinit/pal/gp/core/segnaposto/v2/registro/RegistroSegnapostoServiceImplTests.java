package it.gruppoinit.pal.gp.core.segnaposto.v2.registro;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.infrastructure.packages.IPackageScannerService;
import it.gruppoinit.pal.gp.core.features.infrastructure.packages.PackageScannerService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro.IRegistroSegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro.RegistroSegnapostoServiceImpl;
import it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage.Segnaposto1;
import it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage.Segnaposto2;

public class RegistroSegnapostoServiceImplTests {

    @Test()
    public void seSegnapostoNonTrovatoRestituisceNull() {

	IRegistroSegnaposto svc = new RegistroSegnapostoServiceImpl();
	ISegnaposto s = svc.getSegnaposto("NESSUNO", false);
	Assert.assertNull(s);
    }

    @Test()
    public void scansionaUnPackageArbitrarioERestituisceLeClassiCheImplementanoISegnaposto() {

	IRegistroSegnaposto svc = new RegistroSegnapostoServiceImpl();
	IPackageScannerService packageScanner = new PackageScannerService("it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage");
	svc.inizializza(packageScanner);
	Assert.assertEquals(2, svc.getNumeroSegnapostoRegistrati());
    }

    @Test()
    public void trovaUnSegnapostoRegistrato() {

	IRegistroSegnaposto svc = new RegistroSegnapostoServiceImpl();
	IPackageScannerService packageScanner = new PackageScannerService("it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage");
	svc.inizializza(packageScanner);
	ISegnaposto s = svc.getSegnaposto("SEGNAPOSTO1", false);
	Assert.assertNotNull(s);
	Assert.assertEquals(Segnaposto1.class, s.getClass());
    }

    @Test()
    public void trovaUnSegnapostoRegistratoConArgomentiAncheSeVieneCercatoSenza() {

	IRegistroSegnaposto svc = new RegistroSegnapostoServiceImpl();
	IPackageScannerService packageScanner = new PackageScannerService("it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage");
	svc.inizializza(packageScanner);
	ISegnaposto s = svc.getSegnaposto("SEGNAPOSTO2", false);
	Assert.assertNotNull(s);
	Assert.assertEquals(Segnaposto2.class, s.getClass());
    }
}

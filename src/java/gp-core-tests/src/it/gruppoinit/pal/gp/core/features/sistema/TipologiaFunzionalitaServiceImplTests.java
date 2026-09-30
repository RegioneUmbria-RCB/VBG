package it.gruppoinit.pal.gp.core.features.sistema;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TipologiaFunzionalitaServiceImplTests {

    @Test
    public void VerticalizzazioneNonAttivaStampaDocTipoDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.NonAttiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.NonAttiva());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseStampaDocTipoNonImpostataDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterprise());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneStandardStampaDocTipoNonImpostataDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandard());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseStampaDocTipoJavaDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterpriseConPagineJava());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneStandardStampaDocTipoMicrosoftDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandardConPagineMicrosoft());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneNonAttivaStatisticheDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.NonAttiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.NonAttiva());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneEnterpriseStatisticheNonImpostataDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterprise());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneStandardStatisticheNonImpostataDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandard());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseStatisticheJavaDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterpriseConPagineJava());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneStandardStatisticheMicrosoftDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandardConPagineMicrosoft());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneNonAttivaStampeDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.NonAttiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.NonAttiva());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneEnterpriseStampeNonImpostataDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterprise());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneStandardStampeNonImpostataDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandard());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseStampeJavaDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterpriseConPagineJava());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneStandardStampeMicrosoftDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandardConPagineMicrosoft());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneNonAttivaSchedeDinamicheDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.NonAttiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.NonAttiva());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneEnterpriseSchedeDinamicheNonImpostataDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterprise());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneStandardSchedeDinamicheNonImpostataDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandard());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseSchedeDinamicheJavaDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterpriseConPagineJava());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneStandardSchedeDinamicheMicrosoftDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandardConPagineMicrosoft());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneNonAttivaOneriDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.NonAttiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.NonAttiva());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneEnterpriseOneriNonImpostataDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterprise());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }

    @Test
    public void VerticalizzazioneStandardOneriNonImpostataDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.Attiva());
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandard());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneEnterpriseOneriJavaDeveEssereJava() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaEnterpriseConPagineJava());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI);
	assertEquals(result, TecnologiaPaginaEnum.JAVA);
    }

    @Test
    public void VerticalizzazioneStandardOneriMicrosoftDeveEssereMicrosoft() {

	TipologiaFunzionalitaServiceImpl service = new TipologiaFunzionalitaServiceImpl();
	service.setVerticalizzazioniService(FakeVerticalizzazioneServiceImpl.AttivaConGetStringValorizzato("TEST"));
	service.setVerticalizzazioneTipoInstallazioneService(FakeVerticalizzazioneTipoInstallazioneServiceImpl.AttivaStandardConPagineMicrosoft());
	TecnologiaPaginaEnum result = service.getTecnologiaPagina(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI);
	assertEquals(result, TecnologiaPaginaEnum.MICROSOFT);
    }
}

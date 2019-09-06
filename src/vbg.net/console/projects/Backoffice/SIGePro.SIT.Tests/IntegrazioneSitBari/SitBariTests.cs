using System;
using System.Linq;
using Init.SIGePro.Sit;
using Init.SIGePro.Sit.IntegrazioneSitBari;
using Init.SIGePro.Sit.Manager;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace SIGePro.SIT.Tests.IntegrazioneSitBari
{
	public class SitBariTests
	{
		[TestClass]
		public class GetListaCampiGestiti
		{
			[TestMethod]
			public void Ottengo_solo_la_lista_dei_campi_supportati()
			{
				var connector = new SIT_BARI();

				var listaCampi = connector.GetListaCampiGestiti();

				Assert.IsTrue(listaCampi.Contains(SitIntegrationService.NomiCampiSit.Civico), "SitBari non gestisce il campo Civico");
				Assert.IsTrue(listaCampi.Contains(SitIntegrationService.NomiCampiSit.Esponente), "SitBari non gestisce il campo Esponente");

				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Fabbricato), "SitBari non deve gestire il campo Fabbricato");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.UnitaImmobiliare), "SitBari non deve gestire il campo UnitaImmobiliare");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.CodiceVia), "SitBari non deve gestire il campo CodiceVia");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Colore), "SitBari non deve gestire il campo Colore");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.EsponenteInterno), "SitBari non deve gestire il campo EsponenteInterno");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Foglio), "SitBari non deve gestire il campo Foglio");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Interno), "SitBari non deve gestire il campo Interno");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Km), "SitBari non deve gestire il campo Km");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Particella), "SitBari non deve gestire il campo Particella");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Scala), "SitBari non deve gestire il campo Scala");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Sezione), "SitBari non deve gestire il campo Sezione");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Sub), "SitBari non deve gestire il campo Sub");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Cap), "SitBari non deve gestire il campo Cap");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Frazione), "SitBari non deve gestire il campo Frazione");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Circoscrizione), "SitBari non deve gestire il campo Circoscrizione");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Vincoli), "SitBari non deve gestire il campo Vincoli");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Zone), "SitBari non deve gestire il campo Zone");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.SottoZone), "SitBari non deve gestire il campo SottoZone");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.DatiUrbanistici), "SitBari non deve gestire il campo DatiUrbanistici");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Piani), "SitBari non deve gestire il campo Piani");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Quartieri), "SitBari non deve gestire il campo Quartieri");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.TipoCatasto), "SitBari non deve gestire il campo TipoCatasto");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Piano), "SitBari non deve gestire il campo Piano");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.Quartiere), "SitBari non deve gestire il campo Quartiere");
				Assert.IsTrue(!listaCampi.Contains(SitIntegrationService.NomiCampiSit.CodiceCivico), "SitBari non deve gestire il campo CodiceCivico");
			}
		}

		[TestClass]
		public class ElencoCivici
		{
			[TestMethod]
			public void Ottengo_una_risposta_di_errore()
			{
				var sit = new SIT_BARI();

				sit.DataSit = new Init.SIGePro.Sit.Data.Sit();
				var risultato = sit.ElencoCivici();

				Assert.AreEqual( true, risultato.ReturnValue);		// ???
				Assert.AreEqual<string>("Non è possibile estrarre informazioni sui civici", risultato.Message);
			}
		}

		[TestClass]
		public class ElencoEsponenti
		{
			[TestMethod]
			public void Ottengo_una_risposta_di_errore()
			{
				var sit = new SIT_BARI();

				sit.DataSit = new Init.SIGePro.Sit.Data.Sit();
				var risultato = sit.ElencoEsponenti();

				Assert.AreEqual(true, risultato.ReturnValue);		// ???
				Assert.AreEqual<string>("Non è possibile estrarre informazioni sugli esponenti", risultato.Message);
			}
		}

		[TestClass]
		public class VerificaCivico
		{
			[TestMethod]
			public void Se_non_imposto_il_cod_via_ottengo_un_errore()
			{
				var sit = new SIT_BARI();

				sit.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					IdComune = "idComune",
					CodVia = String.Empty,
					Civico = "1"
				};

				var result = sit.CivicoValidazione();

				Assert.AreEqual(false, result.ReturnValue);
				Assert.AreEqual("Impossibile validare il civico 1, codice via non impostato", result.Message);
			}

			[TestMethod]
			public void Se_non_imposto_il_civico_ottengo_uno_stato_di_errore()
			{
				var sit = new SIT_BARI();

				sit.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					IdComune = "idComune",
					CodVia = "123",
					Civico = ""
				};

				var result = sit.CivicoValidazione();

				// Se non passo il civico il risultato è comunque positivo :(
				Assert.AreEqual(false, result.ReturnValue);
				Assert.AreEqual("Validazione civico fallita, civico non impostato", result.Message);
			}

			[TestMethod]
			public void Se_utilizzo_un_civico_non_numerico_ottengo_un_errore()
			{
				var sit = new SIT_BARI();

				sit.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					IdComune = "idComune",
					CodVia = "123",
					Civico = "asd"
				};

				var result = sit.CivicoValidazione();

				Assert.IsFalse(result.ReturnValue);
				Assert.AreEqual("Validazione civico fallita, il civico asd non è un valore numerico", result.Message);
				Assert.AreEqual("58005", result.MessageCode);
			}

			[TestMethod]
			public void Se_utilizzo_un_civico_non_esistente_la_validazione_fallisce()
			{
				var mock = new Mock<ISitNautilusBariService>();

				mock.Setup( x => x.VerificaEsistenzaIndirizzo( It.IsAny<string>(), It.IsAny<uint>(), It.IsAny<string>()))
					.Returns( (InformazioniEsteseCivico) null );

				var testClass = new SitBariTestClass( mock.Object );

				testClass.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					CodVia = "codvia",
					Civico = "1",
					Esponente = "A"
				};

				var result = testClass.CivicoValidazione();

				Assert.IsFalse(result.ReturnValue);
				Assert.AreEqual("Validazione civico fallita, il civico 1/A non esiste nel sistema SIT in uso", result.Message);
			}

			[TestMethod]
			public void Se_utilizzo_un_civico_esistente_la_validazione_riesce_e_restituisce_un_codcivico_derivante_dai_parametri_passati()
			{
				var mock = new Mock<ISitNautilusBariService>();

				mock.Setup(x => x.VerificaEsistenzaIndirizzo(It.IsAny<string>(), It.IsAny<uint>(), It.IsAny<string>()))
					.Returns(new InformazioniEsteseCivico("circoscrizione", "localita", "codCivico"));

				var testClass = new SitBariTestClass(mock.Object);

				var codVia = "codvia";
				var civico = "1";
				var esponente = "A";

				testClass.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					CodVia = codVia,
					Civico = civico,
					Esponente = esponente
				};

				var result = testClass.CivicoValidazione();

				Assert.AreEqual(true, result.ReturnValue);
				Assert.AreEqual("circoscrizione", testClass.DataSit.Circoscrizione);
				Assert.AreEqual("localita", testClass.DataSit.Frazione);
				Assert.AreEqual(codVia, testClass.DataSit.CodVia);
				Assert.AreEqual(civico, testClass.DataSit.Civico);
				Assert.AreEqual(esponente, testClass.DataSit.Esponente);
				Assert.AreEqual("codCivico", testClass.DataSit.CodCivico);
			}

			[TestMethod]
			public void Invoca_il_proxy_del_web_service_esattamente_una_volta()
			{
				var mock = new Mock<ISitNautilusBariService>();

				mock.Setup(x => x.VerificaEsistenzaIndirizzo(It.IsAny<string>(), It.IsAny<uint>(), It.IsAny<string>()))
					.Returns(new InformazioniEsteseCivico("circoscrizione", "localita", "codCivico"));

				var testClass = new SitBariTestClass(mock.Object);

				var codVia = "codvia";
				var civico = "1";
				var esponente = "A";

				testClass.DataSit = new Init.SIGePro.Sit.Data.Sit
				{
					CodVia = codVia,
					Civico = civico,
					Esponente = esponente
				};

				var result = testClass.CivicoValidazione();

				mock.Verify(x => x.VerificaEsistenzaIndirizzo(codVia, Convert.ToUInt32(civico), esponente), Times.Once());
			}
		}

	}
}

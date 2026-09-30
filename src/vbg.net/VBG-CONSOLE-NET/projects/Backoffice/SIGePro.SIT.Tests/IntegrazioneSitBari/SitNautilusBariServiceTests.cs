using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.Sit.IntegrazioneSitBari;
using Moq;
using Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico;


namespace SIGePro.SIT.Tests.IntegrazioneSitBari
{
	public class SitNautilusBariServiceTests
	{
		[TestClass]
		public class Costruttore
		{
			[TestMethod]
			[ExpectedException(typeof(ArgumentNullException))]
			public void Se_la_configurazione_non_e_impostata_solleva_un_eccezione()
			{
				IConfigurazioneSitBari configurazione = null;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione , serviceAdapter);
			}

			[TestMethod]
			[ExpectedException(typeof(ArgumentNullException))]
			public void Se_il_service_adapter_non_e_impostato_solleva_un_eccezione()
			{
				IConfigurazioneSitBari configurazione = new Mock<IConfigurazioneSitBari>().Object;
				IRicercaCivicoSEIAdapter serviceAdapter = null;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);
			}

			[TestMethod]
			[ExpectedException(typeof(ArgumentException))]
			public void Se_la_configurazione_non_specifica_un_valore_per_CodEnte_solleva_eccezione()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup( x=> x.CodEnte).Returns(String.Empty);
				stubConfigurazione.Setup( x=> x.RequestFrom).Returns("test");
				stubConfigurazione.Setup( x=> x.TipoIndirizzoRicercato).Returns("test");

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);
			}

			[TestMethod]
			[ExpectedException(typeof(ArgumentException))]
			public void Se_la_configurazione_non_specifica_un_valore_per_RequestFrom_solleva_eccezione()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns(String.Empty);
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("test");

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);
			}

			[TestMethod]
			[ExpectedException(typeof(ArgumentException))]
			public void Se_la_configurazione_non_specifica_un_valore_per_TipoIndirizzoRicercato_solleva_eccezione()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns(String.Empty);

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);
			}

			[TestMethod]
			public void Se_il_parametro_di_configurazione_TipoIndirizzoRicercato_e_c_oppure_n_non_solleva_eccezione()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				testedObject = new SitNautilusBariService(stubConfigurazione.Object, serviceAdapter);
			}

			[TestMethod]
			[ExpectedException(typeof(ArgumentOutOfRangeException))]
			public void Se_il_parametro_di_configurazione_TipoIndirizzoRicercato_e_diverso_da_c_oppure_n_solleva_eccezione()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("asd");

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = new Mock<IRicercaCivicoSEIAdapter>().Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);
			}
		}

		[TestClass]
		public class VerificaEsistenzaIndirizzo
		{
			[TestMethod]
			public void Verifica_dei_parametri_passati_al_web_service()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				var mockWebService = new Mock<IRicercaCivicoSEIAdapter>();

				Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType expected = null;

				mockWebService.Setup(x => x.EseguiRicerca(It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()))
							  .Returns((Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.responseType)null)
							  .Callback<requestType>(res => expected = res);

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = mockWebService.Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				var codViaArg = "123";
				var civicoArg = (uint)0;
				var esponenteArg = "esponente";

				testedObject.VerificaEsistenzaIndirizzo(codViaArg, civicoArg, esponenteArg);

				Assert.AreEqual(stubConfigurazione.Object.RequestFrom, expected.request_from);
				Assert.AreEqual<uint>(100, expected.Item.max_rows_ret);
				Assert.AreEqual(true, expected.Item.max_rows_retSpecified);
				Assert.AreEqual(stubConfigurazione.Object.TipoIndirizzoRicercato, expected.Item.indirizzo_ricercato.tipo.ToString());
				Assert.AreEqual(true, expected.Item.indirizzo_ricercato.tipoSpecified);
				Assert.AreEqual(stubConfigurazione.Object.CodEnte, expected.Item.indirizzo_ricercato.ente_cod);
				Assert.AreEqual(codViaArg, expected.Item.indirizzo_ricercato.via_denom_cod);
				Assert.AreEqual(civicoArg, expected.Item.indirizzo_ricercato.numero_civico.civico_num);
				Assert.AreEqual(esponenteArg, expected.Item.indirizzo_ricercato.numero_civico.civico_esp);
			}

			[TestMethod]
			public void Invoca_il_web_service_nautilus_esattamente_una_volta()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				var mockWebService = new Mock<IRicercaCivicoSEIAdapter>();

				mockWebService.Setup(x => x.EseguiRicerca(It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()))
							  .Returns((Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.responseType)null);

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = mockWebService.Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				testedObject.VerificaEsistenzaIndirizzo("123", 0, String.Empty);

				mockWebService.Verify( x => x.EseguiRicerca( It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()), Times.Once() );
			}

			[TestMethod]
			public void Se_il_web_service_rileva_un_errore_nell_xml_ricevuto_restituisce_null()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				var mockWebService = new Mock<IRicercaCivicoSEIAdapter>();

				mockWebService.Setup(x => x.EseguiRicerca(It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()))
							  .Returns((Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.responseType)null);

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = mockWebService.Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				var result = testedObject.VerificaEsistenzaIndirizzo("123", 0, String.Empty);

				Assert.IsNull(result);
			}

			[TestMethod]
			public void Se_il_web_service_non_trova_il_civico_restituisce_null()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				var mockWebService = new Mock<IRicercaCivicoSEIAdapter>();

				mockWebService.Setup(x => x.EseguiRicerca(It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()))
							  .Returns(new Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.responseType
							  {
								  header = new Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.headerType
								  {
									  is_error = new is_error { value = true }
								  }
							  });

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = mockWebService.Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				var result = testedObject.VerificaEsistenzaIndirizzo("123", 0, String.Empty);

				Assert.IsNull(result);
			}

			[TestMethod]
			public void Se_il_web_service_trova_un_indirizzo_restituisce_circoscrizione_e_localita()
			{
				var stubConfigurazione = new Mock<IConfigurazioneSitBari>();

				stubConfigurazione.Setup(x => x.CodEnte).Returns("test");
				stubConfigurazione.Setup(x => x.RequestFrom).Returns("test");
				stubConfigurazione.Setup(x => x.TipoIndirizzoRicercato).Returns("c");

				var mockWebService = new Mock<IRicercaCivicoSEIAdapter>();

				mockWebService.Setup(x => x.EseguiRicerca(It.IsAny<Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.requestType>()))
							  .Returns(new Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.responseType
							  {
								  header = new Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico.headerType
								  {
									  is_error = new is_error { value = false },
									  rows_found = 1
								  },
								  Item = new responseTypeData_outputAbstractTP03_RicercaCivico_response
								  {
									Item = new indirizzo
									{
										civico = new civico
										{
											circoscrizione = "circoscrizione",
											localitafrazione = "localita"
										}
									}
								  }
							  });

				IConfigurazioneSitBari configurazione = stubConfigurazione.Object;
				IRicercaCivicoSEIAdapter serviceAdapter = mockWebService.Object;

				var testedObject = new SitNautilusBariService(configurazione, serviceAdapter);

				var result = testedObject.VerificaEsistenzaIndirizzo("123", 0, String.Empty);

				Assert.IsNotNull(result);
				Assert.AreEqual("circoscrizione", result.Circoscrizione);
				Assert.AreEqual("localita", result.Localita);
			}
		}
	}
}

using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogicTests.GestioneBookmarks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using MovimentiTest.Helpers;
using System;

namespace MovimentiTest.InvioIstanze
{
	[TestClass]
	public class TestInvioConStc
	{
		[TestMethod]
		public void Quando_invio_una_domanda_e_si_verifica_un_errore_di_trasferimento_l_esito_e_InvioFallito()
		{
			var stcMock = new Mock<IStcService>();

			stcMock.Setup(x => x.InserimentoPratica(It.IsAny<InserimentoPraticaRequest>(), It.IsAny<string>(), It.IsAny<SportelloType>()))
				   .Throws(new Exception("Test exception"));

			IComuniService comuniService = MockIIComuniService();
			IIstanzaStcAdapter istanzaStcAdapter = MockIstanzeStcAdapter("123");
			DomandaOnline domanda = MockDomandaOnline();
            IInvioDomandaStrategy invioDomandaStrategy = new InvioDomandaSTCStrategy(comuniService, stcMock.Object, istanzaStcAdapter, new MockBookmarksService());

			var result = invioDomandaStrategy.Send(domanda, "a@a.com");

			Assert.AreEqual<InvioIstanzaResult.TipoEsitoInvio>(InvioIstanzaResult.TipoEsitoInvio.InvioFallito, result.Esito);
			Assert.AreEqual<string>(String.Empty, result.CodiceIstanza);
			Assert.AreEqual<string>(String.Empty, result.NumeroIstanza);
		}

		[TestMethod]
		public void Quando_invio_una_domanda_ad_uno_sportello_che_non_ha_backend_l_esito_e_InvioRiuscitoNoBackend()
		{
			var domanda = MockDomandaOnline();
			var idPratica = domanda.DataKey.ToSerializationCode();


			var stcMock = new Mock<IStcService>();
			stcMock.Setup(x => x.InserimentoPratica(It.IsAny<InserimentoPraticaRequest>(), It.IsAny<string>(), It.IsAny<SportelloType>()))
				   .Returns(new InserimentoPraticaResponse
				   {
					   Items = new object[]{new RiferimentiPraticaType
					   {
						   idPratica = idPratica,
						   numeroPratica = idPratica
					   }}
				   });

			IComuniService comuniService = MockIIComuniService();

			IIstanzaStcAdapter istanzaStcAdapter = MockIstanzeStcAdapter(idPratica);
			IInvioDomandaStrategy invioDomandaStrategy = new InvioDomandaSTCStrategy(comuniService, stcMock.Object, istanzaStcAdapter, new MockBookmarksService());

			var result = invioDomandaStrategy.Send(domanda, "a@a.com");

			Assert.AreEqual<InvioIstanzaResult.TipoEsitoInvio>(InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend, result.Esito);
			Assert.AreEqual<string>(idPratica, result.CodiceIstanza);
			Assert.AreEqual<string>(idPratica, result.NumeroIstanza);
		}

		[TestMethod]
		public void Quando_invio_una_domanda_ma_questa_non_viene_inserita_nel_BE_l_esito_e_InserimentoFallito()
		{
			var domanda = MockDomandaOnline();
			var idPratica = "1";
			var numeroPratica = "2"; 

			var stcMock = new Mock<IStcService>();
			stcMock.Setup(x => x.InserimentoPratica(It.IsAny<InserimentoPraticaRequest>(), It.IsAny<string>(), It.IsAny<SportelloType>()))
				   .Returns(new InserimentoPraticaResponse
				   {
					   Items = new object[]{new RiferimentiPraticaType
					   {
						   idPratica = idPratica,
						   numeroPratica = numeroPratica
					   }}
				   });

			stcMock.Setup(x => x.PraticaEsisteNelBackend(It.IsAny<string>())).Returns(false);

			IComuniService comuniService = MockIIComuniService();

			IIstanzaStcAdapter istanzaStcAdapter = MockIstanzeStcAdapter("123456");
			IInvioDomandaStrategy invioDomandaStrategy = new InvioDomandaSTCStrategy(comuniService, stcMock.Object, istanzaStcAdapter, new MockBookmarksService());

			var result = invioDomandaStrategy.Send(domanda, "a@a.com");

			Assert.AreEqual<InvioIstanzaResult.TipoEsitoInvio>(InvioIstanzaResult.TipoEsitoInvio.InserimentoFallito, result.Esito);
			Assert.AreEqual<string>(String.Empty, result.CodiceIstanza);
			Assert.AreEqual<string>(String.Empty, result.NumeroIstanza);
		}

		[TestMethod]
		public void Quando_invio_una_domanda_e_questa_viene_inserita_nel_BE_l_esito_e_InvioRiuscito()
		{
			var domanda = MockDomandaOnline();
			var idPratica = "1";
			var numeroPratica = "2";

			var stcMock = new Mock<IStcService>();
			stcMock.Setup(x => x.InserimentoPratica(It.IsAny<InserimentoPraticaRequest>(), It.IsAny<string>(), It.IsAny<SportelloType>()))
				   .Returns(new InserimentoPraticaResponse
				   {
					   Items = new object[]{new RiferimentiPraticaType
					   {
						   idPratica = idPratica,
						   numeroPratica = numeroPratica
					   }}
				   });

			stcMock.Setup(x => x.PraticaEsisteNelBackend(It.IsAny<string>())).Returns(true);

			IComuniService comuniService = MockIIComuniService();

			IIstanzaStcAdapter istanzaStcAdapter = MockIstanzeStcAdapter("123456");
            IInvioDomandaStrategy invioDomandaStrategy = new InvioDomandaSTCStrategy(comuniService, stcMock.Object, istanzaStcAdapter, new MockBookmarksService());

			var result = invioDomandaStrategy.Send(domanda, "a@a.com");

			Assert.AreEqual<InvioIstanzaResult.TipoEsitoInvio>(InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito, result.Esito);
			Assert.AreEqual<string>(idPratica, result.CodiceIstanza);
			Assert.AreEqual<string>(numeroPratica, result.NumeroIstanza);
		}


		private DomandaOnline MockDomandaOnline()
		{
			var domanda = new DomandaOnlineMock( PresentazioneIstanzaDataKey.New( "E256", "SS", "GRGNCL79C19G478O", 1), new PresentazioneIstanzaDbV2(), false);
			domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256");
			domanda.WriteInterface.AltriDati.ImpostaIntervento( 1, null);

			return domanda;
		}

		private IIstanzaStcAdapter MockIstanzeStcAdapter(string idPratica)
		{
			var mock = new Mock<IIstanzaStcAdapter>();

			mock.Setup(x => x.Adatta(It.IsAny<DomandaOnline>())).Returns(new DettaglioPraticaType { 
				idPratica = idPratica
			});

			return mock.Object;
		}


		private IComuniService MockIIComuniService()
		{
			var mock = new Mock<IComuniService>();

			mock.Setup(x => x.GetPecComuneAssociato(It.IsAny<string>(), It.IsAny<string>())).Returns("a@a.com");

			return mock.Object;
		}

		private IStcService MockIStcServiceCreator()
		{
			throw new NotImplementedException();
			//var mock = new Mock<IStcService>();

			//mock.Setup(x => x.()).Returns(MockStcServiceInstance());

			//return mock.Object;
		}

		
	}
}

using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using MovimentiTest.Helpers;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
	[TestClass]
	public class SostituzioneSegnapostoRiepilogoServiceTest
	{
		DomandaOnline _domanda;
		SostituzioneSegnapostoRiepilogoService _sostituzioneService;

		[TestInitialize]
		public void TestInitialize()
		{
			this._domanda = new DomandaOnlineMock(PresentazioneIstanzaDataKey.New("alias", "software", "nominativo", 1), new PresentazioneIstanzaDbV2(), false);

			var listaSegnapostoPerTest = new List<ISegnapostoRiepilogo>();
			var mockGeneratoreHtml = new Mock<IGeneratoreHtmlSchedeDinamiche>();

            _sostituzioneService = new SostituzioneSegnapostoRiepilogoService(new StubGeneratoreHtmlSchede());
		}

		[TestMethod]
		public void ProcessaRiepilogo_SostituzioneSegnapostoSchedaConSchedaPresenteNellaDomanda_RestituisceDatiScheda()
		{
			var idScheda = 1;
			var testoScheda = "Scheda 1";
			var template = "<schedaDinamica id='1' />";
			var expected = testoScheda;

			var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma, false, 1)
			};

			this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand( listaSchede, null, null));

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

        [TestMethod]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaConTagApertoEChiuso_RestituisceDatiScheda()
        {
            var idScheda = 1;
			var testoScheda = "Scheda 1";
			var template = "<schedaDinamica id=\"1\"></schedaDinamica>";
			var expected = testoScheda;

			var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
			};

			this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand( listaSchede, null, null));

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
         
        }

		[TestMethod]
		public void ProcessaRiepilogo_SostituzioneSegnapostoSchedaConSchedaNonPresenteNellaDomanda_RestituisceStringaVuota()
		{
			var idScheda = 2;
			var testoScheda = "Scheda 2";
			var template = "<schedaDinamica id='1' />";
			var expected = String.Empty;

			var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1,idScheda, testoScheda, TipoFirmaEnum.NessunaFirma, false, 1)
			};

			this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void Processariepilogo_SostituzioneSegnapostoSchedaConCasingNonCorretto_NonEffettuasostituzione()
		{
			var idScheda = 1;
			var testoScheda = "Scheda 1";
			var template = "<SchedaDinamica id='1' />";
			var expected = template;

			var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma, false, 1)
			};

			this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		[ExpectedException(typeof(ArgomentoSegnapostoNonValidoException))]
		public void Processariepilogo_SostituzioneSegnapostoSchedaConIdentificativoSchedaNonNumerico_SollevaEccezione()
		{
			var idScheda = 1;
			var testoScheda = "Scheda 1";
			var template = "<schedaDinamica id='NaN' />";
			var expected = testoScheda;

			var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma, false, 1)
			};

			this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);
		}

		[TestMethod]
		public void Processariepilogo_SostituzioneSegnapostoDatoConDatoPresenteNellaDomanda_RestituisceValoreDecodificato()
		{
			var idCampoDinamico = 1;
			var valoreCampo = "1";
			var valoreDecodificatoCampo = "Valore Decodificato 1";

			var template = "<campoDinamico id='1' />";
			var expected = valoreDecodificatoCampo;

			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

        [TestMethod]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoCampoConTagApertoEChiuso_RestituisceValoreDecodificato()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<campoDinamico id='1'></campoDinamico>";
            var expected = valoreDecodificatoCampo;

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

            Assert.AreEqual<string>(expected, result);
        }

		[TestMethod]
		public void Processariepilogo_SostituzioneSegnapostoDatoConDatoNonPresenteNellaDomanda_RestituisceStringaVuota()
		{
			var idCampoDinamico = 2;
			var valoreCampo = "2";
			var valoreDecodificatoCampo = "Valore Decodificato 2";

			var template = "<campoDinamico id='1' />";
			var expected = String.Empty;

			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		[ExpectedException(typeof(ArgomentoSegnapostoNonValidoException))]
		public void Processariepilogo_SostituzioneSegnapostoDatoConIdentificativoSchedaNonNumerico_SollevaEccezione()
		{
			var idCampoDinamico = 1;
			var valoreCampo = "1";
			var valoreDecodificatoCampo = "Valore Decodificato 1";

			var template = "<campoDinamico id='NaN' />";
			var expected = valoreDecodificatoCampo;

			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);
		}

		[TestMethod]
		public void Processariepilogo_SostituzioneSegnapostoDatoConCasingNonCorretto_NonEffettuasostituzione()
		{
			var idCampoDinamico = 1;
			var valoreCampo = "1";
			var valoreDecodificatoCampo = "Valore Decodificato 1";

			var template = "<CampoDinamico id='1' />";
			var expected = template;

			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void Processariepilogo_SostituzioneSegnapostoDatoConDatoConValoriMultipli_RestituisceValoreDecodificatoDelPrimoValore()
		{
			var idCampoDinamico = 1;
			var valoreCampo1 = "1";
			var valoreDecodificatoCampo1 = "Valore Decodificato 1";
			var valoreCampo2 = "2";
			var valoreDecodificatoCampo2 = "Valore Decodificato 2";

			var template = "<campoDinamico id='1' />";
			var expected = valoreDecodificatoCampo1;

			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 0, valoreCampo1, valoreDecodificatoCampo1, "nomeCampo");
			_domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico,0, 1, valoreCampo2, valoreDecodificatoCampo2, "nomeCampo");

			var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

			Assert.AreEqual<string>(expected, result);
		}

        [TestMethod]
        public void ProcessaRiepilogo_SostituzioneSegnapostoSchedeDinamicheConDueSchede_RestituisceTitoliSchede()
        {
            var idScheda1 = 1;
            var testoScheda1 = "Scheda 1";
            var idScheda2 = 2;
            var testoScheda2 = "Scheda 2";
            var template = "<schedeDinamiche />";
            var expected = "Scheda 1Scheda 2";

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare(1, idScheda1, testoScheda1, TipoFirmaEnum.NessunaFirma, false, 1),
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda2, testoScheda2, TipoFirmaEnum.NessunaFirma, false, 1)
			};

            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

            Assert.AreEqual<string>(expected, result);
        }
        /*
        [TestMethod]
        public void ProcessaRiepilogo_SostituzioneSegnapostoSchedeDinamicheConUnaSchedaCheRichiedeFirma_RestituisceTitoloDellaSchedaCheNonRichiedeFirma()
        {
            var idScheda1 = 1;
            var testoScheda1 = "Scheda 1";
            var idScheda2 = 2;
            var testoScheda2 = "Scheda 2";
            var template = "<schedeDinamiche />";
            var expected = "Scheda 1";

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> { 
				new ModelloDinamicoInterventoDaSincronizzare{
					Codice = idScheda1,
					Descrizione = testoScheda1,
					Facoltativa = false,
					IdIntervento = 1,
					TipoFirma = TipoFirmaEnum.NessunaFirma
				},
                new ModelloDinamicoInterventoDaSincronizzare{
					Codice = idScheda2,
					Descrizione = testoScheda2,
					Facoltativa = false,
					IdIntervento = 1,
					TipoFirma = TipoFirmaEnum.InteroModello
				}
			};

            this._domanda.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            var result = _sostituzioneService.ProcessaRiepilogo(this._domanda, template);

            Assert.AreEqual<string>(expected, result);
        }
        */
	}
}

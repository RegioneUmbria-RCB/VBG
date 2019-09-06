using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using MovimentiTest.Helpers;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche.Utils;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    [TestClass]
    public class SostituzioneSegnapostoRiepilogoServiceTest
    {
        DomandaOnline _domanda;
        SostituzioneSegnapostoRiepilogoService _sostituzioneService;
        Mock<IDatiDinamiciRepository> _datiDinamiciRepository;

        [TestInitialize]
        public void TestInitialize()
        {
            var db = new PresentazioneIstanzaDbV2();
            this._domanda = new DomandaOnlineMock(PresentazioneIstanzaDataKey.New("alias", "software", "nominativo", 1), db, false);

            var listaSegnapostoPerTest = new List<ISegnapostoRiepilogo>();
            var mockGeneratoreHtml = new Mock<IGeneratoreHtmlSchedeDinamiche>();

            _sostituzioneService = new SostituzioneSegnapostoRiepilogoService(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));
            _datiDinamiciRepository = new Mock<IDatiDinamiciRepository>();

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

            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);
        }

        [TestMethod]
        public void Processariepilogo_SostituzioneSegnapostoDatoConDatoPresenteNellaDomanda_RestituisceValoreDecodificato()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<campoDinamico id='1' />";
            var expected = valoreDecodificatoCampo;

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);
        }

        [TestMethod]
        public void Processariepilogo_SostituzioneSegnapostoDatoConCasingNonCorretto_NonEffettuasostituzione()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<CampoDinamico id='1' />";
            var expected = template;

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo1, valoreDecodificatoCampo1, "nomeCampo");
            _domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 1, valoreCampo2, valoreDecodificatoCampo2, "nomeCampo");

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

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

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaInterventoConTagApertoEChiuso_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeIntervento></schedeIntervento>";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.Is<int>(v => v == 1),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new WsListaModelliDinamiciDomanda
                                        {
                                            SchedeIntervento = new[]{
                                                new SchedaDinamicaInterventoDto
                                                {
                                                    Id = 1
                                                }
                                            }
                                        });

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaInterventoConTagSingolo_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeIntervento />";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.Is<int>(v => v == 1),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new WsListaModelliDinamiciDomanda
                                        {
                                            SchedeIntervento = new[]{
                                                new SchedaDinamicaInterventoDto
                                                {
                                                    Id = 1
                                                }
                                            }
                                        });

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaEndoConTagSingolo_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeEndo id=\"1\" />";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.IsAny<int>(),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new WsListaModelliDinamiciDomanda
                                        {
                                            SchedeEndoprocedimenti = new[]{
                                                new SchedaDinamicaEndoprocedimentoDto
                                                {
                                                    Id = 1
                                                }
                                            }
                                        });

            var result = _sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, _datiDinamiciRepository.Object), template);

            Assert.AreEqual<string>(expected, result);
        }
    }
}

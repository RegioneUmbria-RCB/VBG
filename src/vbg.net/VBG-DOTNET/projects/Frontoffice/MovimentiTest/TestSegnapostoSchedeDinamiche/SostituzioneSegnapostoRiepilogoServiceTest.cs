using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche.Utils;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Moq;
using MovimentiTest.Helpers;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using Xunit;

namespace MovimentiTest
{
    public class StubModello : IDyn2Modello
    {
        public int? Id { get; set; }
        public string CodiceScheda { get; set; }
        public string Descrizione { get; set; }
        public bool Attivo { get; set; }
        public string FkD2bcId { get; set; } = "";
        public string Scriptcode { get; set; } = "";
        public int? Modellomultiplo { get; set; } = 0;
        public int? FlgStoricizza { get; set; } = 0;
        public int? FlgReadonlyWeb { get; set; } = 0;

    }

    public class StrutturaModelloStub : IStrutturaModelloDinamico
    {
        public IDyn2Modello Modello { get; set; }

        public Dictionary<TipoScriptEnum, IDyn2ScriptModello> ScriptsModello { get; set; } = new Dictionary<TipoScriptEnum, IDyn2ScriptModello>();

        public List<IDyn2DettagliModello> Struttura { get; set; } = new List<IDyn2DettagliModello>();

        public Dictionary<int, IDyn2Campo> ListaCampiDinamici { get; set; } = new Dictionary<int, IDyn2Campo>();

        public Dictionary<int, IDyn2TestoModello> ListaTesti { get; set; } = new Dictionary<int, IDyn2TestoModello>();

        public Dictionary<int, Dictionary<TipoScriptEnum, IDyn2ScriptCampo>> ScriptsCampiDinamici { get; set; } = new Dictionary<int, Dictionary<TipoScriptEnum, IDyn2ScriptCampo>>();

        public Dictionary<int, List<IDyn2ProprietaCampo>> ProprietaCampiDinamici { get; set; } = new Dictionary<int, List<IDyn2ProprietaCampo>>();
    }

    public class SostituzioneSegnapostoRiepilogoServiceTest : IDisposable
    {
        private readonly DomandaOnline _domanda;
        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneService;
        private readonly Mock<IDatiDinamiciRepository> _datiDinamiciRepository;
        private readonly Mock<IStrutturaModelloDinamicoRepository> _strutturaRepository;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapter;

        public SostituzioneSegnapostoRiepilogoServiceTest()
        {
            var db = new PresentazioneIstanzaDbV2();
            this._domanda = new DomandaOnlineMock(PresentazioneIstanzaDataKey.New("alias", "software", "nominativo", 1), db, false);
            var mockGeneratoreHtml = SetupMockGeneratoreHtml();

            var accumulatoreNoteService = new MockAccumulatoreNoteService();
            var segnapostoNote = new SegnapostoNoteCampi(accumulatoreNoteService);
            var listaSegnaposto = SetupListaSegnaposto(mockGeneratoreHtml, segnapostoNote);

            this._sostituzioneService = new SostituzioneSegnapostoRiepilogoService(new StubGeneratoreHtmlSchede(), listaSegnaposto);
            this._datiDinamiciRepository = new Mock<IDatiDinamiciRepository>();
            this._strutturaRepository = new Mock<IStrutturaModelloDinamicoRepository>();
            this._istanzaSigeproAdapter = new IstanzaSigeproAdapterService(Enumerable.Empty<IIstanzaSigeproPartialAdapter>(), new MockConfigurazioneVbgRepository());
        }

        private static ListaSegnapostoRiepilogoDomanda SetupListaSegnaposto(Mock<IGeneratoreHtmlSchedeDinamiche> mockGeneratoreHtml, SegnapostoNoteCampi segnapostoNote)
        {
            var listaSegnaposto = new ListaSegnapostoRiepilogoDomanda(segnapostoNote);
            listaSegnaposto.Add(new SegnapostoSchedaDinamica(mockGeneratoreHtml.Object));
            listaSegnaposto.Add(new SegnapostoSchedeDinamiche(mockGeneratoreHtml.Object, new StubParametriGenerazioneRiepilogo(0)));
            listaSegnaposto.Add(new SegnapostoSchedeDinamicheV2(mockGeneratoreHtml.Object, new StubParametriGenerazioneRiepilogo(0)));
            listaSegnaposto.Add(new SegnapostoDatoDinamico());
            listaSegnaposto.Add(new SegnapostoSchedeEndo(mockGeneratoreHtml.Object));
            listaSegnaposto.Add(new SegnapostoSchedeIntervento(mockGeneratoreHtml.Object, new StubParametriGenerazioneRiepilogo(0)));
            return listaSegnaposto;
        }

        private static Mock<IGeneratoreHtmlSchedeDinamiche> SetupMockGeneratoreHtml()
        {
            var mockGeneratoreHtml = new Mock<IGeneratoreHtmlSchedeDinamiche>();

            mockGeneratoreHtml.Setup(x => x.GeneraHtml(It.IsAny<ISchedeDinamicheDomandaAlRiepilogoService>(), It.IsAny<int>(), It.IsAny<int>()))
                             .Returns((ISchedeDinamicheDomandaAlRiepilogoService reader, int idScheda, int indiceMolteplicita) =>
                             {
                                 var row = reader.GetListaModelli().FirstOrDefault(x => x.IdModello == idScheda);
                                 if (row == null)
                                     return String.Empty;
                                 return row.Descrizione;
                             });

            mockGeneratoreHtml.Setup(x => x.GeneraHtmlDelleSchedeDellaDomanda(It.IsAny<ISchedeDinamicheDomandaAlRiepilogoService>(), It.IsAny<GenerazioneHtmlSchedeOptions>()))
                             .Returns((ISchedeDinamicheDomandaAlRiepilogoService reader, GenerazioneHtmlSchedeOptions options) =>
                             {
                                 return String.Join("", reader.GetListaModelli().Select(x => x.Descrizione).ToArray());
                             });

            mockGeneratoreHtml.Setup(x => x.GeneraHtmlSchedeIntervento(It.IsAny<ISchedeDinamicheDomandaAlRiepilogoService>(), It.IsAny<GenerazioneHtmlSchedeOptions>()))
                             .Returns((ISchedeDinamicheDomandaAlRiepilogoService reader, GenerazioneHtmlSchedeOptions options) =>
                             {
                                 return String.Join("", reader.GetListaModelliIntervento().Select(x => x.Descrizione).ToArray());
                             });

            mockGeneratoreHtml.Setup(x => x.GeneraHtmlSchedaEndoprocedimento(It.IsAny<ISchedeDinamicheDomandaAlRiepilogoService>(), It.IsAny<int>()))
                             .Returns((ISchedeDinamicheDomandaAlRiepilogoService reader, int idEndo) =>
                             {
                                 return String.Join("", reader.GetListaModelliEndo(idEndo).Select(x => x.Descrizione).ToArray());
                             });

            return mockGeneratoreHtml;
        }

        public void Dispose() { }

        [Fact]
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

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
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

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);

        }

        [Fact]
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

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
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

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
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

            var exception = Assert.Throws<ArgomentoSegnapostoNonValidoException>(() =>
            {
                this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);
            });

            Assert.Contains("L'identificativo scheda impostato in un segnaposto non è un numero valido, testo del segnaposto: <schedaDinamica id='NaN' />", exception.Message);
        }

        [Fact]
        public void Processariepilogo_SostituzioneSegnapostoDatoConDatoPresenteNellaDomanda_RestituisceValoreDecodificato()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<campoDinamico id='1' />";
            var expected = valoreDecodificatoCampo;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoCampoConTagApertoEChiuso_RestituisceValoreDecodificato()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<campoDinamico id='1'></campoDinamico>";
            var expected = valoreDecodificatoCampo;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void Processariepilogo_SostituzioneSegnapostoDatoConDatoNonPresenteNellaDomanda_RestituisceStringaVuota()
        {
            var idCampoDinamico = 2;
            var valoreCampo = "2";
            var valoreDecodificatoCampo = "Valore Decodificato 2";

            var template = "<campoDinamico id='1' />";
            var expected = String.Empty;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void Processariepilogo_SostituzioneSegnapostoDatoConIdentificativoSchedaNonNumerico_SollevaEccezione()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<campoDinamico id='NaN' />";
            var expected = valoreDecodificatoCampo;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var exception = Assert.Throws<ArgomentoSegnapostoNonValidoException>(() =>
            {
                this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);
            });

            Assert.Contains("L'identificativo campo impostato in un segnaposto non è un numero valido, testo del segnaposto: <campoDinamico id='NaN' />", exception.Message);
        }

        [Fact]
        public void Processariepilogo_SostituzioneSegnapostoDatoConCasingNonCorretto_NonEffettuasostituzione()
        {
            var idCampoDinamico = 1;
            var valoreCampo = "1";
            var valoreDecodificatoCampo = "Valore Decodificato 1";

            var template = "<CampoDinamico id='1' />";
            var expected = template;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo, valoreDecodificatoCampo, "nomeCampo");

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void Processariepilogo_SostituzioneSegnapostoDatoConDatoConValoriMultipli_RestituisceValoreDecodificatoDelPrimoValore()
        {
            var idCampoDinamico = 1;
            var valoreCampo1 = "1";
            var valoreDecodificatoCampo1 = "Valore Decodificato 1";
            var valoreCampo2 = "2";
            var valoreDecodificatoCampo2 = "Valore Decodificato 2";

            var template = "<campoDinamico id='1' />";
            var expected = valoreDecodificatoCampo1;

            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 0, valoreCampo1, valoreDecodificatoCampo1, "nomeCampo");
            this._domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(idCampoDinamico, 0, 1, valoreCampo2, valoreDecodificatoCampo2, "nomeCampo");

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void ProcessaRiepilogo_SostituzioneSegnapostoSchedeDinamicheConDueSchede_RestituisceTitoliSchede()
        {
            var idScheda1 = 1;
            var testoScheda1 = "Scheda 1";
            var idScheda2 = 2;
            var testoScheda2 = "Scheda 2";
            var template = "<schedeDinamiche />";
            var expected = "<div id='datiDinamici'>Scheda 1Scheda 2</div>";

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda1, testoScheda1, TipoFirmaEnum.NessunaFirma, false, 1),
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda2, testoScheda2, TipoFirmaEnum.NessunaFirma, false, 1)
            };

            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaInterventoConTagApertoEChiuso_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeIntervento></schedeIntervento>";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256", "54024");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.Is<int>(v => v == 1),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new ListaModelliDinamiciDomandaDto
                                        {
                                            SchedeIntervento = new List<SchedaDinamicaInterventoDto>{
                                                new SchedaDinamicaInterventoDto
                                                {
                                                    Id = 1
                                                }
                                            }
                                        });

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaInterventoConTagSingolo_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeIntervento />";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256", "54024");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.Is<int>(v => v == 1),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new ListaModelliDinamiciDomandaDto
                                        {
                                            SchedeIntervento = new List<SchedaDinamicaInterventoDto>{
                                                new SchedaDinamicaInterventoDto
                                                {
                                                    Id = idScheda
                                                }
                                            }
                                        });

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }

        [Fact]
        public void ProcessaRiepilgo_SostituzioneconSegnapostoSchedaEndoConTagSingolo_RestituisceDatiScheda()
        {
            var idScheda = 1;
            var testoScheda = "Scheda 1";
            var template = "<schedeEndo id=\"1\" />";
            var expected = testoScheda;

            var listaSchede = new List<ModelloDinamicoInterventoDaSincronizzare> {
                new ModelloDinamicoInterventoDaSincronizzare(1, idScheda, testoScheda, TipoFirmaEnum.NessunaFirma,false, 1)
            };

            this._domanda.WriteInterface.AltriDati.ImpostaCodiceComune("E256", "54024");
            this._domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            this._domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(listaSchede, null, null));

            this._datiDinamiciRepository.Setup(x => x.GetSchedeDaInterventoEEndo(It.IsAny<int>(),
                                                                                 It.IsAny<IEnumerable<int>>(),
                                                                                 It.IsAny<IEnumerable<string>>(),
                                                                                 UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No))
                                        .Returns(new ListaModelliDinamiciDomandaDto
                                        {
                                            SchedeEndoprocedimenti = new List<SchedaDinamicaEndoprocedimentoDto>{
                                                new SchedaDinamicaEndoprocedimentoDto
                                                {
                                                    Id = 1
                                                }
                                            }
                                        });

            var result = this._sostituzioneService.ProcessaRiepilogo(new DomandaOnlineDatiDinamiciReader(this._domanda, this._datiDinamiciRepository.Object, this._strutturaRepository.Object, this._istanzaSigeproAdapter), template);

            Assert.Equal(expected, result);
        }
    }
}

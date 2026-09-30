using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using MovimentiTest.Adapters;
using System;
using System.Collections.Generic;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using Xunit;

namespace MovimentiTest.Adapters
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


    public class StrutturaModelloStubReader : IStrutturaModelloDinamicoRepository
    {
        private readonly IStrutturaModelloDinamico _strutturaModello;

        public StrutturaModelloStubReader(IStrutturaModelloDinamico strutturaModello)
        {
            this._strutturaModello = strutturaModello;
        }

        public IStrutturaModelloDinamico GetStrutturaModelloDinamico(int idModello) => this._strutturaModello;
    }

    public class CampoDinamicoStub : IDyn2Campo
    {
        public CampoDinamicoStub(int id, string etichetta, string nomeCampo)
        {
            this.Id = id;
            this.Etichetta = etichetta;
            this.Nomecampo = nomeCampo;
        }

        public int? Id { get; set; }
        public string Etichetta { get; set; }
        public string Nomecampo { get; set; }
        public string Descrizione { get; set; }
        public string Tipodato { get; set; }
        public int? Obbligatorio { get; set; } = 0;
        public string FkD2bcId { get; set; } = "";
    }

    public class DettagliModelloStub : IDyn2DettagliModello
    {
        public int? Id { get; set; }
        public int? FkD2mtId { get; set; }
        public int? FkD2mdtId { get; set; }
        public int? FkD2cId { get; set; }
        public int? Ordine { get; set; }
        public int? FlgVisibileWeb { get; set; }
        public string FkD2bcId { get; set; } = "";

        public int? Posverticale { get; set; } = 0;

        public int? Posorizzontale { get; set; } = 0;

        public int? FlgMultiplo => 0;

        public int? FlgSpezzaTabella => 0;

        public string Tags { get; set; }
    }
}

public class TestCondizioneAttivazioneDatiDinamiciAdapter : ICondizioneAttivazioneDatiDinamiciAdapter
{
    public bool Verificata(IDomandaOnlineReadInterface domanda) => true;
}

public class DatiDinamiciAdapterTests
{
    private readonly PresentazioneIstanzaDbV2 _db;
    private readonly DomandaOnlineReadInterface _readInterface;
    private readonly DatiDinamiciAdapter _adapter;
    private readonly int _idCampoDinamico;

    public DatiDinamiciAdapterTests()
    {
        this._idCampoDinamico = 123;
        this._db = new PresentazioneIstanzaDbV2();
        this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);
        this._db.Dyn2Modelli.AddDyn2ModelliRow(0, "Nome scheda", true, 0, 0, true);

        var reader = new StrutturaModelloStubReader(this.CreaStrutturaModello());
        this._adapter = new DatiDinamiciAdapter(reader, new TestCondizioneAttivazioneDatiDinamiciAdapter());
    }

    private StrutturaModelloStub CreaStrutturaModello()
    {
        var campo = new CampoDinamicoStub(this._idCampoDinamico, "Etichetta", "Nome campo");

        var struttura = new StrutturaModelloStub
        {
            Modello = new StubModello()
            {
                Id = 0,
                CodiceScheda = "Codice scheda",
                Descrizione = "Descrizione",
                Attivo = true
            }
        };

        struttura.Struttura.Add(new DettagliModelloStub
        {
            FkD2cId = campo.Id.Value,
        });
        struttura.ListaCampiDinamici.Add(campo.Id.Value, campo);

        return struttura;
    }

    [Fact]
    public void L_indice_del_valore_di_un_campo_dinamico_viene_riportato_nella_domanda_stc()
    {
        var domandaStc = new DettaglioPraticaType();

        // Creo i dati della scheda
        var indice = 2;
        var indicemolteplicita = 0;
        var valore = "valore";
        var valoreDecodificato = "valoreDecodificato";

        this._db.Dyn2Dati.AddDyn2DatiRow(this._idCampoDinamico, indice, indicemolteplicita, valore, valoreDecodificato, String.Empty);

        this._adapter.Adapt(this._readInterface, domandaStc);

        Assert.Single(domandaStc.schede);
        Assert.Single(domandaStc.schede[0].campi);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
        Assert.Single(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
        Assert.Equal(indice, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indice);
        Assert.True(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceSpecified);
        Assert.Equal(indicemolteplicita, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceMolteplicita);
        Assert.True(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceMolteplicitaSpecified);
        Assert.Equal(valore, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].codice);
        Assert.Equal(valoreDecodificato, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].descrizione);
    }

    [Fact]
    public void Se_ho_un_valore_all_indice_2_allora_non_viene_creato_un_campo_con_valori_vuoti_all_indice_1()
    {
        var domandaStc = new DettaglioPraticaType();

        // Creo i dati della scheda
        var indice = 0;
        var indicemolteplicita = 2;
        var valore = "valore";
        var valoreDecodificato = "valoreDecodificato";

        this._db.Dyn2Dati.AddDyn2DatiRow(this._idCampoDinamico, indice, indicemolteplicita, valore, valoreDecodificato, String.Empty);

        this._adapter.Adapt(this._readInterface, domandaStc);

        Assert.Single(domandaStc.schede);
        Assert.Single(domandaStc.schede[0].campi);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente);
        Assert.NotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
        Assert.Single(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
        Assert.Equal(indice, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indice);
        Assert.True(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceSpecified);
        Assert.Equal(valore, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].codice);
        Assert.Equal(valoreDecodificato, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].descrizione);
    }
}


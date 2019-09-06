using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
{
    public class FvgDatabase
    {
        [XmlRoot(ElementName = "scheda")]
        public class SchedaDinamica
        {
            public int Id { get; set; }
            public string Nome { get; set; }
            public string Descrizione { get; set; }
            public bool MostraNelBackoffice { get; set; }
            public bool Compilata { get; set; }

            public SchedaDinamica(int id, string nome, string descrizione, bool mostraNelBackoffice)
            {
                this.Id = id;
                this.Nome = nome;
                this.Descrizione = descrizione;
                this.MostraNelBackoffice = mostraNelBackoffice;

                this.Compilata = false;
            }

            // Utilizzare solo per la serializzazione
            [Obsolete("Utilizzare solo per la serializzazione", true)]
            public SchedaDinamica()
            {
            }
        }

        [XmlRoot(ElementName = "valore")]
        public class ValoreCampoDinamico
        {
            [XmlElement(ElementName = "id")]
            public int IdCampo { get; set; }
            [XmlElement(ElementName = "nome")]
            public string NomeCampo { get; set; }
            [XmlElement(ElementName = "indiceMolteplicita")]
            public int IndiceMolteplicita { get; set; }
            [XmlElement(ElementName = "valore")]
            public string Valore { get; set; }
            [XmlElement(ElementName = "valoreDecodificato")]
            public string ValoreDecodificato { get; set; }
        
            public ValoreCampoDinamico(int idCampo, string nomeCampo, int indiceMolteplicita, string valore, string valoreDecodificato)
            {
                this.IdCampo = idCampo;
                this.NomeCampo = nomeCampo;
                this.IndiceMolteplicita = indiceMolteplicita;
                this.Valore = valore;
                this.ValoreDecodificato = valoreDecodificato;
            }

            // Utilizzare solo per la serializzazione
            [Obsolete("Utilizzare solo per la serializzazione", true)]
            public ValoreCampoDinamico()
            {

            }

            public void SetValore(string valore, string valoreDecodificato)
            {
                this.Valore = valore;
                this.ValoreDecodificato = valoreDecodificato;
            }
        }



        [XmlRoot(ElementName = "fvgDataSet")]
        public class Flyweight
        {
            [XmlElement(ElementName = "scheda")]
            public List<SchedaDinamica> Schede { get; set; }
            [XmlElement(ElementName = "campoDinamico")]
            public List<ValoreCampoDinamico> ValoriCampiDinamici { get; set; }

            public Flyweight()
            {
                this.Schede = new List<SchedaDinamica>();
                this.ValoriCampiDinamici = new List<ValoreCampoDinamico>();
            }
        }


        public class ValoreCampoDinamicoReadonly : IIstanzeDyn2Dati, IValoreDatoDinamicoRiepilogo
        {
            public string Idcomune { get => "FVGSOL"; set { } }
            public int? Codiceistanza { get => -1; set { } }
            public int? FkD2cId { get; set; }
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
            public string Valore { get; set; }
            public string Valoredecodificato { get; set; }
            public string ValoreDecodificato { get; set; }

            public ValoreCampoDinamicoReadonly(ValoreCampoDinamico valore)
            {
                this.FkD2cId = valore.IdCampo;
                this.Indice = 0;
                this.IndiceMolteplicita = valore.IndiceMolteplicita;
                this.Valore = valore.Valore;
                this.Valoredecodificato = valore.ValoreDecodificato;
                this.ValoreDecodificato = valore.ValoreDecodificato;
            }
        }

        Flyweight _statoInterno;
        IFvgDatabasePersistenceMedium _storage;

        public bool ContieneValori => this._statoInterno.ValoriCampiDinamici.Any();

        internal FvgDatabase(Flyweight statoInterno)
        {
            this._statoInterno = statoInterno;
        }

        public FvgDatabase(IFvgDatabasePersistenceMedium storage)
            :this(new Flyweight())
        {
            this.SetPesistenceMedium(storage);
        }

        internal FvgDatabase()
            :this(new Flyweight())
        {

        }

        public void ImpostaValoreCampo(int idCampo, string nomeCampo, string valore, string valoreDecodificato)
        {
            this.ImpostaValoreCampo(idCampo, nomeCampo, 0, valore, valoreDecodificato);
        }

        public void ImpostaValoreCampo(int idCampo, string nomeCampo, int indiceMolteplicita, string valore, string valoreDecodificato)
        {
            var campo = this._statoInterno
                            .ValoriCampiDinamici.Where(x => x.IdCampo == idCampo &&
                                                            x.IndiceMolteplicita == indiceMolteplicita)
                            .FirstOrDefault();

            if (campo == null)
            {
                this._statoInterno.ValoriCampiDinamici.Add(new ValoreCampoDinamico(idCampo, nomeCampo, indiceMolteplicita, valore, valoreDecodificato));
            } else
            {
                campo.SetValore(valore, valoreDecodificato);
            }
        }

        internal void SincronizzaSchede(SchedaDinamicaDto[] schede)
        {
            var schedeDaAggiungere = new List<SchedaDinamicaDto>();
            var schedeDaEliminare = this._statoInterno.Schede.Select(x => x.Id).ToList();

            foreach (var scheda in schede)
            {
                if (this._statoInterno.Schede.Where( x => x.Id == scheda.Id).Any())
                {
                    schedeDaEliminare.Remove(scheda.Id);
                }
                else
                {
                    schedeDaAggiungere.Add(scheda);
                }
            }

            // Elimino le schede che non sono più richieste
            foreach (var id in schedeDaEliminare)
            {
                var daEliminare = this._statoInterno.Schede.Where(x => x.Id == id).ToList();

                foreach (var eliminata in daEliminare)
                {
                    this._statoInterno.Schede.Remove(eliminata);
                }
            }

            // Aggiungo le schede che non sono state trovate tra quelle già presenti
            foreach (var scheda in schedeDaAggiungere)
            {
                this._statoInterno.Schede.Add(new SchedaDinamica(scheda.Id, scheda.CodiceScheda, scheda.Descrizione, scheda.FvgMostraNelBackoffice));
            }

        }

        internal void InizializzaValoriDaManagedData(IEnumerable<string> listaCampiDelModulo, IFVGWebServiceProxy webServiceProxy)
        {
            var reader = new FvgDatiDinamiciManagedDataReader(listaCampiDelModulo, webServiceProxy);

            if (this._storage == null)
            {
                return;
            }

            var datiEstrattiDalManagedData = reader.GetValoriDatiDinamici(this._storage.CodiceIstanza);

            foreach(var datoEstratto in datiEstrattiDalManagedData)
            {
                this.ImpostaValoreCampo(datoEstratto.IdCampo, datoEstratto.NomeCampo, datoEstratto.IndiceMolteplicita, datoEstratto.Valore, datoEstratto.ValoreDecodificato);
            }
        }

        public IEnumerable<IIstanzeDyn2Dati> GetListaValori()
        {
            return this._statoInterno.ValoriCampiDinamici.Select(x => new ValoreCampoDinamicoReadonly(x));
        }

        public ValoreCampoDinamicoReadonly GetValoreSingoloCampo(int idCampo, int indiceMolteplicita)
        {
            return this._statoInterno
                        .ValoriCampiDinamici
                        .Where(x => x.IdCampo == idCampo && x.IndiceMolteplicita == indiceMolteplicita)
                        .Select(x => new ValoreCampoDinamicoReadonly(x))
                        .FirstOrDefault();
        }

        internal bool IsSchedaCompilata(int idModello)
        {
            return this._statoInterno.Schede.Where(x => x.Id == idModello && x.Compilata).Any();
        }

        internal void SetSchedaCompilata(int idModello)
        {
            this._statoInterno
                .Schede
                .Where(x => x.Id == idModello)
                .ToList()
                .ForEach( x => x.Compilata = true);
        }
        
        public void SetPesistenceMedium(IFvgDatabasePersistenceMedium storage)
        {
            this._statoInterno = storage.Load();
            this._storage = storage;
        }

        public void Salva()
        {
            if (this._storage != null)
            {
                this._storage.Save(this._statoInterno);
            }
        }

        internal void EliminaValoriCampo(int id)
        {
            this._statoInterno.ValoriCampiDinamici.RemoveAll(x => x.IdCampo == id);
        }

    }
}

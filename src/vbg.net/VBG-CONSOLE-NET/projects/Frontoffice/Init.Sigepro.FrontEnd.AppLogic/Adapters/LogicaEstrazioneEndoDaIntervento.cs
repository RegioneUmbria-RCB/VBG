using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    public class LogicaEstrazioneEndoDaIntervento
    {
        protected class FamigliaIndexer
        {
            public int Codice { get; set; }
            public string Descrizione { get; set; }
            public Dictionary<int, TipoEndoIndexer> TipiEndo { get; private set; }

            public FamigliaIndexer()
            {
                this.TipiEndo = new Dictionary<int, TipoEndoIndexer>();
            }
        }

        protected class TipoEndoIndexer
        {
            public int Codice { get; set; }
            public string Descrizione { get; set; }
            public List<EndoprocedimentoDto> Endo { get; private set; }

            public TipoEndoIndexer()
            {
                this.Endo = new List<EndoprocedimentoDto>();
            }
        }


        public List<FamigliaEndoprocedimentoDto> FamiglieEndoPrincipale { get; protected set; }
        public List<FamigliaEndoprocedimentoDto> FamiglieEndoAttivati { get; protected set; }
        public List<FamigliaEndoprocedimentoDto> FamiglieEndoFacoltativi { get; protected set; }

        public List<EndoprocedimentoDto> EndoPrincipali { get; protected set; }
        public List<EndoprocedimentoDto> EndoAttivati { get; protected set; }
        public List<EndoprocedimentoDto> EndoAttivabili { get; protected set; }

        public Dictionary<int, EndoprocedimentoDto> IndiceEndo { get; protected set; }

        public LogicaEstrazioneEndoDaIntervento(FamigliaEndoprocedimentoDto[] endoIntervento)
        {
            this.IndiceEndo = new Dictionary<int, EndoprocedimentoDto>();

            var endoPrincipaliIndexerDict = new Dictionary<int, FamigliaIndexer>();
            var endoAttivatiIndexerDict = new Dictionary<int, FamigliaIndexer>();
            var endoFacoltativiIndexerDict = new Dictionary<int, FamigliaIndexer>();

            foreach (var famigliaEndo in endoIntervento)
            {
                foreach (var tipoEndo in famigliaEndo.TipiEndoprocedimenti)
                {
                    foreach (var endo in tipoEndo.Endoprocedimenti)
                    {
                        if (!this.IndiceEndo.ContainsKey(endo.Codice))
                            this.IndiceEndo.Add(endo.Codice, endo);

                        if (endo.Principale)
                        {
                            endo.Richiesto = true;

                            this.AggiungiEndo(famigliaEndo, tipoEndo, endo, endoPrincipaliIndexerDict);
                            continue;
                        }

                        if (endo.Richiesto)
                        {
                            this.AggiungiEndo(famigliaEndo, tipoEndo, endo, endoAttivatiIndexerDict);
                            continue;
                        }

                        this.AggiungiEndo(famigliaEndo, tipoEndo, endo, endoFacoltativiIndexerDict);
                    }
                }
            }

            // Rigenero le strutture dai dati ottenuti
            this.FamiglieEndoPrincipale = new List<FamigliaEndoprocedimentoDto>();
            this.EndoPrincipali = new List<EndoprocedimentoDto>();

            this.FamiglieEndoAttivati = new List<FamigliaEndoprocedimentoDto>();
            this.EndoAttivati = new List<EndoprocedimentoDto>();

            this.FamiglieEndoFacoltativi = new List<FamigliaEndoprocedimentoDto>();
            this.EndoAttivabili = new List<EndoprocedimentoDto>();

            this.RigeneraListaDaDictionary(endoPrincipaliIndexerDict, this.FamiglieEndoPrincipale, this.EndoPrincipali);
            this.RigeneraListaDaDictionary(endoAttivatiIndexerDict, this.FamiglieEndoAttivati, this.EndoAttivati);
            this.RigeneraListaDaDictionary(endoFacoltativiIndexerDict, this.FamiglieEndoFacoltativi, this.EndoAttivabili);
        }

        /// <summary>
        /// Aggiunge un endoprocedimento alla lista passata come parametro "lista"
        /// </summary>
        /// <param name="famiglia">famiglie dell'endo</param>
        /// <param name="tipo">Tipo endo</param>
        /// <param name="endo">endo</param>
        /// <param name="lista">lista a cui aggiungere l'endo</param>
        protected void AggiungiEndo(FamigliaEndoprocedimentoDto famiglia, TipoEndoprocedimentoDto tipo, EndoprocedimentoDto endo, Dictionary<int, FamigliaIndexer> lista)
        {
            if (!lista.ContainsKey(famiglia.Codice))
            {
                var nuovafamiglia = new FamigliaIndexer
                {
                    Codice = famiglia.Codice,
                    Descrizione = famiglia.Descrizione
                };

                lista.Add(nuovafamiglia.Codice, nuovafamiglia);
            }

            var famigliaTrovata = lista[famiglia.Codice];

            if (!famigliaTrovata.TipiEndo.ContainsKey(tipo.Codice))
            {
                var nuovoEndo = new TipoEndoIndexer
                {
                    Codice = tipo.Codice,
                    Descrizione = tipo.Descrizione
                };

                famigliaTrovata.TipiEndo.Add(nuovoEndo.Codice, nuovoEndo);
            }

            famigliaTrovata.TipiEndo[tipo.Codice].Endo.Add(endo);
        }

        protected void RigeneraListaDaDictionary(Dictionary<int, FamigliaIndexer> struttura, List<FamigliaEndoprocedimentoDto> listaFamiglie, List<EndoprocedimentoDto> listaEndo)
        {
            foreach (var famiglia in struttura.Values)
            {
                listaFamiglie.Add(new FamigliaEndoprocedimentoDto
                {
                    Codice = famiglia.Codice,
                    Descrizione = famiglia.Descrizione
                });

                var tmpTipiList = new List<TipoEndoprocedimentoDto>();

                foreach (var tipo in famiglia.TipiEndo.Values)
                {
                    tmpTipiList.Add(new TipoEndoprocedimentoDto
                    {
                        Codice = tipo.Codice,
                        Descrizione = tipo.Descrizione
                    });

                    var tmpEndoList = new List<EndoprocedimentoDto>();

                    foreach (var endo in tipo.Endo)
                    {
                        tmpEndoList.Add(endo);
                        listaEndo.Add(endo);
                    }

                    tmpEndoList.Sort((a, b) =>
                    {
                        var ordine = a.Ordine - b.Ordine;

                        if (ordine == 0)
                        {
                            ordine = b.Descrizione.CompareTo(a.Descrizione);
                        }

                        return ordine;
                    });

                    tmpTipiList.Last().Endoprocedimenti = tmpEndoList.ToArray();
                }

                listaFamiglie.Last().TipiEndoprocedimenti = tmpTipiList.ToArray();

            }
        }
    }
}

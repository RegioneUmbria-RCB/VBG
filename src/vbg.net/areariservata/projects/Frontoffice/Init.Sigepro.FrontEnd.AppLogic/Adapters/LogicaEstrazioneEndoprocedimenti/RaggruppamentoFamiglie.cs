using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.LogicaEstrazioneEndoprocedimenti
{
    internal class RaggruppamentoFamiglie
    {
        protected class TempFamiglia
        {
            public int Codice { get; set; }
            public string Descrizione { get; set; }
            public ConcurrentDictionary<int, TempTipoEndo> TipiEndo { get; private set; }

            public TempFamiglia()
            {
                TipiEndo = new ConcurrentDictionary<int, TempTipoEndo>();
            }

            internal void AggiungiEndoprocedimento(TipoEndoprocedimentoDto tipo, EndoprocedimentoDto endo)
            {
                var tipoTrovato = this.TipiEndo.GetOrAdd(tipo.Codice, x => new TempTipoEndo
                {
                    Codice = tipo.Codice,
                    Descrizione = tipo.Descrizione
                });

                tipoTrovato.Endo.Add(endo);
            }
        }

        protected class TempTipoEndo
        {
            public int Codice { get; set; }
            public string Descrizione { get; set; }
            public List<EndoprocedimentoDto> Endo { get; private set; }

            public TempTipoEndo()
            {
                Endo = new List<EndoprocedimentoDto>();
            }
        }

        private ConcurrentDictionary<int, TempFamiglia> _famiglie = new ConcurrentDictionary<int, TempFamiglia>();

        public void AggiungiEndoprocedimento(FamigliaEndoprocedimentoDto famiglia, TipoEndoprocedimentoDto tipo, EndoprocedimentoDto endo)
        {
            var famigliaTrovata = this._famiglie.GetOrAdd(famiglia.Codice, x => new TempFamiglia
            {
                Codice = famiglia.Codice,
                Descrizione = famiglia.Descrizione
            });

            famigliaTrovata.AggiungiEndoprocedimento(tipo, endo);
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetListaFamiglie()
        {
            var listaFamiglie = this._famiglie.Values.Select(famiglia => new FamigliaEndoprocedimentoDto
            {
                Codice = famiglia.Codice,
                Descrizione = famiglia.Descrizione,
                TipiEndoprocedimenti = famiglia.TipiEndo
                                                .Values
                                                .Select(tipo => new TipoEndoprocedimentoDto
                                                {
                                                    Codice = tipo.Codice,
                                                    Descrizione = tipo.Descrizione,
                                                    Endoprocedimenti = tipo.Endo
                                                                            .OrderBy(x => x.Ordine)
                                                                            .ThenBy(x => x.Descrizione)
                                                                            .ToArray()
                                                }).ToArray()
            });

            var endoDaAggiungere = listaFamiglie.SelectMany(x => x.TipiEndoprocedimenti.SelectMany(y => y.Endoprocedimenti));

            return listaFamiglie;
        }

        public List<EndoprocedimentoDto> EstraiListaEndo()
        {
            return this._famiglie.Values.SelectMany(famiglia => famiglia.TipiEndo.Values.SelectMany(tipo => tipo.Endo)).ToList();
        }
    }
}

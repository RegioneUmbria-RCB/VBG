using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.LogicaEstrazioneEndoprocedimenti
{
    public class LogicaEstrazioneEndoDaIntervento
    {
        public List<FamigliaEndoprocedimentoDto> FamiglieEndoPrincipale { get; protected set; }
        public List<FamigliaEndoprocedimentoDto> FamiglieEndoAttivati { get; protected set; }
        public List<FamigliaEndoprocedimentoDto> FamiglieEndoFacoltativi { get; protected set; }

        public List<EndoprocedimentoDto> EndoPrincipali { get; protected set; }
        public List<EndoprocedimentoDto> EndoAttivati { get; protected set; }
        public List<EndoprocedimentoDto> EndoAttivabili { get; protected set; }

        public LogicaEstrazioneEndoDaIntervento(FamigliaEndoprocedimentoDto[] listaFamiglieEndoprocedimenti)
        {
            var tmpEndoPrincipali = new RaggruppamentoFamiglie();
            var tmpEndoAttivati = new RaggruppamentoFamiglie();
            var tmpEndoFacoltativi = new RaggruppamentoFamiglie();


            // raggruppo gli endo in base al tipo: principali, attivati e facoltativi
            foreach (var famigliaEndo in listaFamiglieEndoprocedimenti)
            {
                foreach (var tipoEndo in famigliaEndo.TipiEndoprocedimenti)
                {
                    foreach (var endo in tipoEndo.Endoprocedimenti)
                    {
                        if (endo.Principale)
                        {
                            endo.Richiesto = true;

                            tmpEndoPrincipali.AggiungiEndoprocedimento(famigliaEndo, tipoEndo, endo);

                            continue;
                        }

                        if (endo.Richiesto)
                        {
                            tmpEndoAttivati.AggiungiEndoprocedimento(famigliaEndo, tipoEndo, endo);

                            continue;
                        }

                        tmpEndoFacoltativi.AggiungiEndoprocedimento(famigliaEndo, tipoEndo, endo);
                    }
                }
            }

            // "Inverto" le liste di ciascun raggruppamento di endo ottenendo così le famiglie di endo principali, attivati e facoltativi
            this.FamiglieEndoPrincipale = tmpEndoPrincipali.GetListaFamiglie().ToList();
            this.FamiglieEndoAttivati = tmpEndoAttivati.GetListaFamiglie().ToList();
            this.FamiglieEndoFacoltativi = tmpEndoFacoltativi.GetListaFamiglie().ToList();

            this.EndoPrincipali = tmpEndoPrincipali.EstraiListaEndo();
            this.EndoAttivati = tmpEndoAttivati.EstraiListaEndo();
            this.EndoAttivabili = tmpEndoFacoltativi.EstraiListaEndo();
        }
    }
}

using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.LogicaEstrazioneEndoprocedimenti
{
    public class LogicaEstrazioneEndoFacoltativi
    {
        public FamigliaEndoprocedimentoDto[] EndoFacoltativi { get { return this._endoFacoltativi.ToArray(); } }

        private IEnumerable<FamigliaEndoprocedimentoDto> _endoFacoltativi;
        private Dictionary<int, EndoprocedimentoDto> _endoprocedimentiByIdIndex = new Dictionary<int, EndoprocedimentoDto>();
        private Dictionary<int, TipoEndoprocedimentoDto> _tipoByIdEndoIndex = new Dictionary<int, TipoEndoprocedimentoDto>();
        private Dictionary<int, FamigliaEndoprocedimentoDto> _famigliaByIdTipoEndoIndex = new Dictionary<int, FamigliaEndoprocedimentoDto>();

        public LogicaEstrazioneEndoFacoltativi(IEnumerable<FamigliaEndoprocedimentoDto> endoFacoltativi)
        {
            this._endoFacoltativi = endoFacoltativi;

            this.CostruisciIndiciEndoprocedimenti();
        }

        private void CostruisciIndiciEndoprocedimenti()
        {
            this._endoprocedimentiByIdIndex = new Dictionary<int, EndoprocedimentoDto>();
            this._famigliaByIdTipoEndoIndex = new Dictionary<int, FamigliaEndoprocedimentoDto>();
            this._tipoByIdEndoIndex = new Dictionary<int, TipoEndoprocedimentoDto>();

            foreach (var famiglia in this._endoFacoltativi)
            {
                foreach (var tipo in famiglia.TipiEndoprocedimenti)
                {
                    if (!this._famigliaByIdTipoEndoIndex.ContainsKey(tipo.Codice))
                        this._famigliaByIdTipoEndoIndex.Add(tipo.Codice, famiglia);

                    foreach (var endo in tipo.Endoprocedimenti)
                    {
                        if (!this._endoprocedimentiByIdIndex.ContainsKey(endo.Codice))
                        {
                            this._endoprocedimentiByIdIndex.Add(endo.Codice, endo);
                            this._tipoByIdEndoIndex.Add(endo.Codice, tipo);
                        }
                    }
                }
            }
        }

        public void RimuoviEndoGiaPresenti(IEnumerable<int> codiciEndoprocedimentiDaRimuovere)
        {
            foreach (var codiceEndo in codiciEndoprocedimentiDaRimuovere)
            {
                if (!this._tipoByIdEndoIndex.ContainsKey(codiceEndo))
                    continue;

                var endo = this._endoprocedimentiByIdIndex[codiceEndo];
                var tipoEndo = this._tipoByIdEndoIndex[codiceEndo];
                var listaEndo = tipoEndo.Endoprocedimenti.ToList();

                listaEndo.Remove(endo);

                // Se il tipo endo non contiene più endoprocedimenti lo rimuovo
                if (listaEndo.Count != 0)
                {
                    tipoEndo.Endoprocedimenti = listaEndo.ToList();
                    continue;
                }

                var famigliaEndo = this._famigliaByIdTipoEndoIndex[tipoEndo.Codice];

                var listaTipiEndo = famigliaEndo.TipiEndoprocedimenti.ToList();

                listaTipiEndo.Remove(tipoEndo);

                if (listaTipiEndo.Count != 0)
                {
                    famigliaEndo.TipiEndoprocedimenti = listaTipiEndo.ToList();
                    continue;
                }

                // Se la famiglia non contiene più tipi endo la rimuovo
                var listaFamiglie = this._endoFacoltativi.ToList();

                listaFamiglie.Remove(famigliaEndo);

                this._endoFacoltativi = listaFamiglie.ToArray();
            }
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetListaFamiglieDaIdEndoSelezionati(List<int> idSelezionati)
        {
            var raggruppamentoFamiglie = new RaggruppamentoFamiglie();

            foreach (var famiglia in this._endoFacoltativi)
            {
                foreach (var tipo in famiglia.TipiEndoprocedimenti)
                {
                    foreach (var endo in tipo.Endoprocedimenti)
                    {
                        if (!idSelezionati.Contains(endo.Codice))
                            continue;

                        raggruppamentoFamiglie.AggiungiEndoprocedimento(famiglia, tipo, endo);
                    }
                }
            }

            return raggruppamentoFamiglie.GetListaFamiglie();

        }
    }
}

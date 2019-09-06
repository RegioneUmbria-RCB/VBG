using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.LogicaEstrazioneEndoprocedimenti
{
    public class LogicaEstrazioneEndoFacoltativi
    {
        public FamigliaEndoprocedimentoDto[] EndoFacoltativi { get { return _endoFacoltativi; } }

        FamigliaEndoprocedimentoDto[] _endoFacoltativi;
        Dictionary<int, EndoprocedimentoDto> _endoprocedimentiByIdIndex;
        Dictionary<int, TipoEndoprocedimentoDto> _tipoByIdEndoIndex;
        Dictionary<int, FamigliaEndoprocedimentoDto> _famigliaByIdTipoEndoIndex;

        public LogicaEstrazioneEndoFacoltativi(FamigliaEndoprocedimentoDto[] endoFacoltativi)
        {
            _endoFacoltativi = endoFacoltativi;

            CostruisciIndiciEndoprocedimenti();
        }

        private void CostruisciIndiciEndoprocedimenti()
        {
            _endoprocedimentiByIdIndex = new Dictionary<int, EndoprocedimentoDto>();
            _famigliaByIdTipoEndoIndex = new Dictionary<int, FamigliaEndoprocedimentoDto>();
            _tipoByIdEndoIndex = new Dictionary<int, TipoEndoprocedimentoDto>();

            foreach (var famiglia in _endoFacoltativi)
            {
                foreach (var tipo in famiglia.TipiEndoprocedimenti)
                {
                    if (!_famigliaByIdTipoEndoIndex.ContainsKey(tipo.Codice))
                        _famigliaByIdTipoEndoIndex.Add(tipo.Codice, famiglia);

                    foreach (var endo in tipo.Endoprocedimenti)
                    {
                        if (!_endoprocedimentiByIdIndex.ContainsKey(endo.Codice))
                        {
                            _endoprocedimentiByIdIndex.Add(endo.Codice, endo);
                            _tipoByIdEndoIndex.Add(endo.Codice, tipo);
                        }
                    }
                }
            }
        }

        public void RimuoviEndoGiaPresenti(IEnumerable<int> codiciEndoprocedimentiDaRimuovere)
        {
            foreach (var codiceEndo in codiciEndoprocedimentiDaRimuovere)
            {
                if (!_tipoByIdEndoIndex.ContainsKey(codiceEndo))
                    continue;

                var endo = _endoprocedimentiByIdIndex[codiceEndo];
                var tipoEndo = _tipoByIdEndoIndex[codiceEndo];
                var listaEndo = tipoEndo.Endoprocedimenti.ToList();

                listaEndo.Remove(endo);

                // Se il tipo endo non contiene più endoprocedimenti lo rimuovo
                if (listaEndo.Count != 0)
                {
                    tipoEndo.Endoprocedimenti = listaEndo.ToArray();
                    continue;
                }

                var famigliaEndo = _famigliaByIdTipoEndoIndex[tipoEndo.Codice];

                var listaTipiEndo = famigliaEndo.TipiEndoprocedimenti.ToList();

                listaTipiEndo.Remove(tipoEndo);

                if (listaTipiEndo.Count != 0)
                {
                    famigliaEndo.TipiEndoprocedimenti = listaTipiEndo.ToArray();
                    continue;
                }

                // Se la famiglia non contiene più tipi endo la rimuovo
                var listaFamiglie = _endoFacoltativi.ToList();

                listaFamiglie.Remove(famigliaEndo);

                _endoFacoltativi = listaFamiglie.ToArray();
            }
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetListaFamiglieDaIdEndoSelezionati(List<int> idSelezionati)
        {
            var raggruppamentoFamiglie = new RaggruppamentoFamiglie();

            foreach (var famiglia in _endoFacoltativi)
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

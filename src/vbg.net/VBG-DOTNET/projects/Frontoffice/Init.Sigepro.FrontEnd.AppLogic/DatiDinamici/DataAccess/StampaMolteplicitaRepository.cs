using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public class StampaMolteplicitaRepository : IDyn2DatiRepository
    {
        public class DummyValore : IValoreCampo
        {
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
            public string Valore { get; set; }
            public string Valoredecodificato { get; set; }
        }

        private readonly IDyn2DatiRepository _baseRepo;
        private readonly int _indiceMolteplicita;
        private readonly IStrutturaModelloDinamico _cache;

        public StampaMolteplicitaRepository(IDyn2DatiRepository baseRepo, int indiceMolteplicita, IStrutturaModelloDinamico cache)
        {
            this._baseRepo = baseRepo;
            this._indiceMolteplicita = indiceMolteplicita;
            this._cache = cache;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            this._baseRepo.EliminaValoriCampi(idModello, campiDaEliminare);
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var idCampiNonMultipli = this._cache.Struttura.Where(x => x.FkD2cId.HasValue && x.FlgMultiplo.GetValueOrDefault(0) == 0)
                                                             .Select(x => x.FkD2cId.Value);

            // Recupero tutti i valori dei dati dinamici della scheda
            var tmpRVal = this._baseRepo.GetValoriCampiDaIdModello(idModello, indiceModello);
            var newRVal = new SerializableDictionary<int, IEnumerable<IValoreCampo>>();

            // Dato che devo stampare un documento sposto all'indice 0 tutti i campi che si trovano all'indice 
            // che viene stampato
            foreach (var key in tmpRVal.Keys)
            {

                var valoreCampoTrovato = false;
                var valoriCampiDaRimuovere = new List<IValoreCampo>();

                var el = tmpRVal[key].ToList();

                for (int i = 0; i < el.Count; i++)
                {
                    var campo = el.ElementAt(i);

                    // Se il campo non è multiplo, devo prendere il valore all'indice 0 indipendentemente dall'indice che sto stampando
                    // il controllo campo.IndiceMolteplicita == 0 è probabilmente ridondante
                    if (idCampiNonMultipli.Contains(key) && campo.IndiceMolteplicita == 0)
                    {
                        valoreCampoTrovato = true;
                        continue;
                    }

                    if (campo.IndiceMolteplicita == this._indiceMolteplicita)
                    {
                        campo.IndiceMolteplicita = 0;
                        valoreCampoTrovato = true;
                        continue;
                    }

                    valoriCampiDaRimuovere.Add(campo);
                }

                // Rimuovo i valori dei campi che si trovano ad un altro indice di molteplicità
                foreach (var valoreCampo in valoriCampiDaRimuovere)
                    el.Remove(valoreCampo);


                // Se il campo non
                if (!valoreCampoTrovato)
                {
                    var dati = new DummyValore
                    {
                        Indice = 0,
                        IndiceMolteplicita = 0,
                        Valore = string.Empty,
                        Valoredecodificato = string.Empty
                    };

                    el.Add(dati);
                }

                newRVal.Add(key, el);
            }


            return newRVal;
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            this._baseRepo.SalvaValoriCampi(idModello, campiDaSalvare);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> idCampiNonVisibili)
        {
            this._baseRepo.SalvaCampiNonVisibili(idModello, idCampiNonVisibili);
        }
    }
}

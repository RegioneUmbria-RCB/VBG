using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess.Visura
{
    public class VisuraDatiRepository : IDyn2DatiRepository
    {
        private class ValoreDaVisura : IValoreCampo
        {
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
            public string Valore { get; set; }
            public string Valoredecodificato { get; set; }
        }

        private readonly Istanze _istanza;

        public VisuraDatiRepository(Istanze istanza)
        {
            _istanza = istanza;
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var dd = this._istanza.IstanzeDyn2Dati
                         .GroupBy(x => x.FkD2cId.Value)
                         .ToDictionary(x => x.Key, x => x.Select(y => new ValoreDaVisura
                         {
                             Indice = y.Indice,
                             IndiceMolteplicita = y.IndiceMolteplicita,
                             Valore = y.Valore,
                             Valoredecodificato = y.Valoredecodificato
                         }).Cast<IValoreCampo>());

            return new SerializableDictionary<int, IEnumerable<IValoreCampo>>(dd);
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            throw new NotImplementedException();
        }

        public void EliminaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            throw new NotImplementedException();
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
            throw new NotImplementedException();
        }
    }
}

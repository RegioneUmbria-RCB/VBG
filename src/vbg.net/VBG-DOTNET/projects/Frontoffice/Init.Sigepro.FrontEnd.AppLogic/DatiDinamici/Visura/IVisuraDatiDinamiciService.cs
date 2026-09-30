using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System.Collections.Generic;
using VBG.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public interface IVisuraDatiDinamiciService
    {
        ModelloDinamicoIstanza GetModello(Istanze istanza, int idModello);
        IEnumerable<VisuraTitoloModelloDinamicoIstanza> GetTitoliModelli(Istanze istanza);
    }
}

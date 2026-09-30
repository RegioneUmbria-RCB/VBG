using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using VBG.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
    public class MovimentiIstanzeManager
    {
        private readonly IVisuraService _visuraService;

        public MovimentiIstanzeManager(IVisuraService visuraService)
        {
            this._visuraService = visuraService;
        }

        public IClasseContestoModelloDinamico LeggiIstanza(int codiceIstanza)
        {
            return this._visuraService.GetById(codiceIstanza, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false });
        }
    }
}

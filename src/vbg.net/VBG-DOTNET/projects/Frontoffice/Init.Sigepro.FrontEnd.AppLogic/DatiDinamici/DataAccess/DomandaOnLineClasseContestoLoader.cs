using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public class DomandaOnLineClasseContestoLoader : IClasseContestoLoader, IQueryLocalizzazioniFactory
    {
        private readonly Istanze _cacheIstanza;

        public DomandaOnLineClasseContestoLoader(Istanze cacheIstanza)
        {
            this._cacheIstanza = cacheIstanza;
        }

        public IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            return new QueryLocalizzazioniDaClasseIstanze(this._cacheIstanza);
        }

        public IClasseContestoModelloDinamico LoadClass()
        {
            return this._cacheIstanza;
        }
    }
}

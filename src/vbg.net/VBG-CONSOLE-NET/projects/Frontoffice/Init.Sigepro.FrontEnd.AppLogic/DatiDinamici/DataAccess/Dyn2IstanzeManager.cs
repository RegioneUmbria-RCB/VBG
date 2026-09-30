using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public class Dyn2IstanzeManager : IIstanzeManager
    {
        private static class Constants
        {
            public const string HttpContextKey = "Dyn2IstanzeManager._cacheIstanza";
        }

        IstanzaSigeproAdapter _adapter;

        public Dyn2IstanzeManager(IstanzaSigeproAdapter adapter)
        {
            _adapter = adapter;
        }


        #region IIstanzeManager Members

        Istanze _cacheIstanza = null;

        private Istanze GetIstanzaInCache()
        {
            var hasContext = HttpContext.Current != null;

            if (hasContext)
            {
                _cacheIstanza = (Istanze)HttpContext.Current.Items[Constants.HttpContextKey];
            }

            if (_cacheIstanza == null)
            {
                _cacheIstanza = _adapter.Adatta();
            }

            if (hasContext)
            {
                HttpContext.Current.Items[Constants.HttpContextKey] = _cacheIstanza;
            }

            return _cacheIstanza;
        }

        public IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
        {
            return GetIstanzaInCache();
        }

        #endregion

        internal QueryLocalizzazioniDaClasseIstanze GetQueryLocalizzazioni()
        {
            return new QueryLocalizzazioniDaClasseIstanze(GetIstanzaInCache());
        }
    }
}

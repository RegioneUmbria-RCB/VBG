using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
	public class Dyn2IstanzeManager : IIstanzeManager
	{
		IstanzaSigeproAdapter _adapter;

		public Dyn2IstanzeManager( IstanzaSigeproAdapter adapter )
		{
			_adapter = adapter;
		}


        #region IIstanzeManager Members

        Istanze _cacheIstanza = null;

        private Istanze GetIstanzaInCache()
        {
            if (_cacheIstanza == null)
            {
                _cacheIstanza = _adapter.Adatta();
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
            return new QueryLocalizzazioniDaClasseIstanze(this.GetIstanzaInCache());
        }
    }
}

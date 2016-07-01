using Init.Sigepro.FrontEnd.AppLogic.Adapters;
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

		IClasseContestoModelloDinamico _cacheIstanza = null;

		public IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
		{

			if (_cacheIstanza == null)
			{
				_cacheIstanza = _adapter.Adatta();
			}

			return _cacheIstanza;
		}

		#endregion
	}
}

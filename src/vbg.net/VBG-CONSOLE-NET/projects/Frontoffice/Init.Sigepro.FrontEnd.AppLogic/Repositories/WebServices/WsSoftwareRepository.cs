using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsSoftwareRepository : ISoftwareRepository
	{

		AreaRiservataServiceCreator _serviceCreator;

		public WsSoftwareRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public Software GetById(string aliasComune, string codice)
		{
			string CACHE_KEY = "CACHE_DATI_SOFTWARE_" + codice;

			if (!CacheHelper.KeyExists(CACHE_KEY))
			{
				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					var rVal = ws.Service.GetDatiSoftware(ws.Token, codice);

					return CacheHelper.AddEntry(CACHE_KEY, rVal);
				}
			}

			return CacheHelper.GetEntry<Software>(CACHE_KEY);
		}
	}
}

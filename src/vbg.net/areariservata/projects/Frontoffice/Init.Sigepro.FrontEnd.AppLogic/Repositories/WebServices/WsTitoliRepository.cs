using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsTitoliRepository : ITitoliRepository
	{
		const string CACHE_KEY = "TITOLI_CACHE_KEY_";


		AreaRiservataServiceCreator _serviceCreator;

		public WsTitoliRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public Titoli[] GetList(string aliasComune)
		{
			var cacheKey = CACHE_KEY + aliasComune;

			if (CacheHelper.KeyExists(cacheKey))
				return CacheHelper.GetEntry<Titoli[]>(cacheKey);

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var rVal = ws.Service.GetListaTitoli(ws.Token);
				return CacheHelper.AddEntry(cacheKey, rVal);
			}
		}
	}
}

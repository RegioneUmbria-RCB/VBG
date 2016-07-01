using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsFormeGiuridicheRepository : WsAreaRiservataRepositoryBase, IFormeGiuridicheRepository
	{
		const string SESSION_KEY = "SESSION_KEY_FORME_GIURIDICHE_";
		IAliasResolver _aliasResolver;

		public WsFormeGiuridicheRepository(AreaRiservataServiceCreator sc, IAliasResolver aliasResolver)
			: base(sc)
		{
			this._aliasResolver = aliasResolver;
		}

		public FormeGiuridiche[] GetList(string aliasComune)
		{
			string key = SESSION_KEY + aliasComune;

			if (!CacheHelper.KeyExists(key))
			{
				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					var val = ws.Service.GetListaFormeGiuridiche(ws.Token);

					return CacheHelper.AddEntry(key, val);
				}
			}

			return CacheHelper.GetEntry<FormeGiuridiche[]>(key);
		}


		public FormeGiuridiche GetById(string id)
		{
			var alias = this._aliasResolver.AliasComune;

			return this.GetList(alias).Where(x => x.CODICEFORMAGIURIDICA == id).FirstOrDefault();
		}
	}
}

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using log4net;

	internal class WsConfigurazioneAreaRiservataRepository : WsAreaRiservataRepositoryBase, IConfigurazioneAreaRiservataRepository
	{
		const string SESSION_KEY = "CONFIGURAZIONE_PROXY_CACHE_KEY_";

		ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneAreaRiservataRepository));

		public WsConfigurazioneAreaRiservataRepository(AreaRiservataServiceCreator sc)
			: base(sc)
		{

		}

		public ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software)
		{
			var sessionKey = SESSION_KEY + idComune + "_" + software;

			if (SessionHelper.KeyExists(sessionKey))
				return SessionHelper.GetEntry<ConfigurazioneAreaRiservataDto>(sessionKey);

			_log.Debug("Dati di configurazione del FrontEnd non presenti in cache. Lettura della configurazione da web service");

			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				try
				{
					var cfg = ws.Service.LeggiConfigurazioneFrontoffice(ws.Token, software);

					SessionHelper.AddEntry(sessionKey, cfg);

					return cfg;
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}
	}
}

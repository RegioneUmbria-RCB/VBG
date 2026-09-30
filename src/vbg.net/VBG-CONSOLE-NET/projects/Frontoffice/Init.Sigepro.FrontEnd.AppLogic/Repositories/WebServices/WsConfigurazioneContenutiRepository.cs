using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsConfigurazioneContenutiRepository : WsAreaRiservataRepositoryBase, IConfigurazioneContenutiRepository
	{
		const string SESSION_KEY = "CONFIGURAZIONE_CONTENUTI_SESSION_KEY_";

		ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneContenutiRepository));

		public WsConfigurazioneContenutiRepository(AreaRiservataServiceCreator sc)
			: base(sc)
		{

		}


		public ConfigurazioneContenutiDto GetConfigurazione(string alias, string software)
		{
			var sessionKey = SESSION_KEY + alias + "_" + software;

			if (SessionHelper.KeyExists(sessionKey))
				return SessionHelper.GetEntry<ConfigurazioneContenutiDto>(sessionKey);

			_log.Debug("Dati di configurazione del FrontEnd non presenti in cache. Lettura della configurazione da web service");

			using (var ws = _serviceCreator.CreateClient(alias))
			{
				var cfg = ws.Service.GetConfigurazioneContenutiFrontoffice(ws.Token, software);
				
				return SessionHelper.AddEntry(sessionKey, cfg);
			}
		}
	}
}

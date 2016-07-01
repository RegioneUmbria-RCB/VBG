using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.SigeproAutorizzazioniService;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
	internal class AutorizzazioniMercatiRepository
	{
		private static class Constants
		{
			public const string ApplicationKey = "AutorizzazioniMercatiRepository.ListaEnti";
		}


		ILog _log = LogManager.GetLogger("AutorizzazioniMercatiRepository");
		AutorizzazioniMercatiServiceCreator _serviceCreator;

		public AutorizzazioniMercatiRepository(AutorizzazioniMercatiServiceCreator serviceCreator)
		{
			this._serviceCreator = serviceCreator;
		}

		public IEnumerable<ListaAutorizzazioniItem> GetListaAutorizzazioni(int codiceAnagrafe, string[] registri, string stringaFormattazione, int codiceIntervento)
		{
			using (var ws = this._serviceCreator.CreateClient())
			{
				try
				{
					return ws.Service.GetAutorizzazioniConCodiceIntervento(ws.Token, registri, codiceAnagrafe, stringaFormattazione, codiceIntervento);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("errore durante l'invocazione di GetAutorizzazioniConCodiceIntervento: {0}", ex.ToString());

					ws.Service.Abort();

					throw;
				}
			}
		}

		public IEnumerable<EnteAutorizzazione> GetListaEnti()
		{
			if (!CacheHelper.KeyExists(Constants.ApplicationKey))
			{
				CacheHelper.AddEntry(Constants.ApplicationKey, GetListaEntiInternal());
			}

			return CacheHelper.GetEntry<IEnumerable<EnteAutorizzazione>>(Constants.ApplicationKey);
		}

		private object GetListaEntiInternal()
		{
			using (var ws = this._serviceCreator.CreateClient())
			{
				try
				{
					return ws.Service.GetEnti(ws.Token);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("errore durante l'invocazione di GetEnti: {0}", ex.ToString());

					ws.Service.Abort();

					throw;
				}
			}
		}

		public DettagliAutorizzazione GetDettagliAutorizzazione(int idAutorizzazione, int codiceIntervento)
		{
			using (var ws = this._serviceCreator.CreateClient())
			{
				try
				{
					return ws.Service.GetAutorizzazioneConCodiceIntervento(ws.Token, idAutorizzazione, codiceIntervento);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("errore durante l'invocazione di GetAutorizzazioneConCodiceIntervento: {0}", ex.ToString());

					ws.Service.Abort();

					throw;
				}
			}
		}
	}
}

// -----------------------------------------------------------------------
// <copyright file="TipiSoggettoRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.AppLogic.Common;

	public interface ITipiSoggettoRepository
	{
		TipiSoggetto GetById(int id);
		IEnumerable<TipiSoggetto> GetObbligatori(int? codiceIntervento);
		IEnumerable<TipiSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento);
		IEnumerable<TipiSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento);
	}

	internal class TipiSoggettoRepository : ITipiSoggettoRepository
	{
		private static class Constants
		{
			public const string SessionKeyById = "tipisoggettorepository:byid:{0}:{1}:{2}";
			public const string SessionKeyList = "tipisoggettorepository:list:{0}:{1}:{2}:{3}";
			public const string TipoSoggettoPersonaFisica = "F";
			public const string TipoSoggettoPersonaGiuridica = "G";
		}

		AreaRiservataServiceCreator _serviceCreator;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		public TipiSoggettoRepository(AreaRiservataServiceCreator serviceCreator, IAliasSoftwareResolver aliasSoftwareResolver)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public TipiSoggetto GetById(int id)
		{
			var sessionKey = GetSessionKeyById(id);

			if (SessionHelper.KeyExists(sessionKey))
			{
				return SessionHelper.GetEntry<TipiSoggetto>(sessionKey);
			}

			using (var ws = CreateClient())
			{
				var val = ws.Service.GetTipoSoggettoById(ws.Token, id);

				return SessionHelper.AddEntry(sessionKey, val);
			}
		}

		public IEnumerable<TipiSoggetto> GetObbligatori(int? codiceIntervento)
		{
			var list = GetList(Constants.TipoSoggettoPersonaFisica, codiceIntervento).Union(
					GetList(Constants.TipoSoggettoPersonaGiuridica, codiceIntervento)
				);

			var res = from ts in list
					  where ts.FO_OBBLIGATORIO == "1"
					  select ts;

			if (res.Count() == 0)
				return new List<TipiSoggetto>();

			return res;
		}

		public IEnumerable<TipiSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento)
		{
			return GetList(Constants.TipoSoggettoPersonaFisica, codiceIntervento);
		}

		public IEnumerable<TipiSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento)
		{
			return GetList(Constants.TipoSoggettoPersonaGiuridica, codiceIntervento);
		}

		// -------------------------------------

		private string GetSessionKeyById(int id)
		{
			var alias = this._aliasSoftwareResolver.AliasComune;
			var software = this._aliasSoftwareResolver.Software;

			return String.Format(Constants.SessionKeyById, alias, software, id);
		}

		private string GetSessionKeyList(string tipoAnagrafe,int? codiceIntervento)
		{
			var alias = this._aliasSoftwareResolver.AliasComune;
			var software = this._aliasSoftwareResolver.Software;

			return String.Format(Constants.SessionKeyList, alias, software, tipoAnagrafe, codiceIntervento.HasValue ? codiceIntervento.Value.ToString() : "*");
		}

		private ServiceInstance<AreaRiservataServiceSoapClient> CreateClient()
		{
			var aliasComune = this._aliasSoftwareResolver.AliasComune;

			return _serviceCreator.CreateClient(aliasComune);
		}

		private IEnumerable<TipiSoggetto> GetList(string tipoAnagrafe, int? codiceIntervento)
		{
			var sessionKey = GetSessionKeyList(tipoAnagrafe, codiceIntervento);
			var software = this._aliasSoftwareResolver.Software;

			if (SessionHelper.KeyExists(sessionKey))
			{
				return SessionHelper.GetEntry<TipiSoggetto[]>(sessionKey);
			}

			using (var ws = CreateClient())
			{
				var val = ws.Service.GetListaTipiSoggettoFrontoffice(ws.Token, software, tipoAnagrafe, codiceIntervento);

				return SessionHelper.AddEntry(sessionKey, val);
			}
		}

	}
}

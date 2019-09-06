// -----------------------------------------------------------------------
// <copyright file="IFacctUrlBuilder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Cart
{
	using System;
	using System.Collections.Generic;
	using System.Text;
	using System.Web;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
	using log4net;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
    using Init.Sigepro.FrontEnd.AppLogic.Common;

    public interface IFacctUrlBuilder
	{
		string BuildUrl(int idDomanda, string codiceComune, int idIntervento, string codiceBdr);
	}

	public class FacctUrlBuilder : IFacctUrlBuilder
	{
		private static class Constants
		{
			public const string NomeParametroToken = "Token";
			public const string NomeParametroIdComune = "idcomunealias";
			public const string NomeParametroIdIntervento = "idAlberoProc";
			public const string NomeParametroIdDomanda = "idDomandaFo";
			public const string NomeParametroReturnTo = "ReturnTo";
			public const string NomeParametroCodiceAttivitaBdr = "codiceAttivitaBdr";
			public const string NomeParametroSoftware = "software";
			public const string NomeParametroAppMitt = "APPMITT";
			public const string NomeParametroCodiceComune = "codicecomune";
			public const string ValoreParametroAppMitt = "MS";
		}


		#region Exceptions

		[Serializable]
		public class ConfigurazioneRedirectFacctNonCorrettaException : Exception
		{
			internal ConfigurazioneRedirectFacctNonCorrettaException() :
				base("L'intervento corrente è collegato ad un codice BDR ma nei parametri di configurazione non è stato impostato l'url dell'applicazione FACCT. Verificare il parametro URL_APPLICAZIONE_FACCT della verticalizzazione AREA_RISERVATA.")
			{ }
			internal ConfigurazioneRedirectFacctNonCorrettaException(string message) : base(message) { }
			internal ConfigurazioneRedirectFacctNonCorrettaException(string message, Exception inner) : base(message, inner) { }
			protected ConfigurazioneRedirectFacctNonCorrettaException(
			  System.Runtime.Serialization.SerializationInfo info,
			  System.Runtime.Serialization.StreamingContext context)
				: base(info, context) { }
		}

		#endregion

		IConfigurazione<ParametriCart> _configurazioneCart;
		ILog _log = LogManager.GetLogger(typeof(FacctUrlBuilder));
        NavigationService _navigationService;
        IAliasSoftwareResolver _aliasSoftwareResolver;
        ITokenResolver _tokenResolver;


        public FacctUrlBuilder(NavigationService navigationService, IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazione<ParametriCart> configurazioneCart, ITokenResolver tokenResolver)
		{
			Condition.Requires(configurazioneCart, "configurazioneCart").IsNotNull();

			this._navigationService = navigationService;
			this._configurazioneCart = configurazioneCart;
            this._tokenResolver = tokenResolver;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public string BuildUrl(int idDomanda, string codiceComune, int idIntervento, string codiceBdr)
		{
			if (!_configurazioneCart.Parametri.IsUrlCompletoApplicazioneFacctDefined)
			{
				_log.Error($"L'intervento della domanda con codice {idDomanda} ha un codice CART associato , ma nei parametri di configurazione non è stato impostato l'url dell'applicazione FACCT. Verificare il parametro URL_APPLICAZIONE_FACCT della verticalizzazione AREA_RISERVATA");

				throw new ConfigurazioneRedirectFacctNonCorrettaException();
			}

            var url = UrlBuilder.Url(_configurazioneCart.Parametri.UrlCompletoApplicazioneFacct, x => {
                x.Add(Constants.NomeParametroIdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(Constants.NomeParametroIdIntervento, idIntervento.ToString());
				x.Add(Constants.NomeParametroIdDomanda , idDomanda.ToString());
                x.Add(Constants.NomeParametroReturnTo , BuildReturnToUrl());
                x.Add(Constants.NomeParametroCodiceAttivitaBdr , codiceBdr);
                x.Add(Constants.NomeParametroSoftware , this._aliasSoftwareResolver.Software);
                x.Add(Constants.NomeParametroToken , this._tokenResolver.Token);
                x.Add(Constants.NomeParametroCodiceComune, codiceComune);
                x.Add(Constants.NomeParametroAppMitt , Constants.ValoreParametroAppMitt);

            });

			return url;
		}

		private string BuildReturnToUrl()
		{
			return this._navigationService.GetPathCompletoReservedDefaultPage();
		}
        
	}

}

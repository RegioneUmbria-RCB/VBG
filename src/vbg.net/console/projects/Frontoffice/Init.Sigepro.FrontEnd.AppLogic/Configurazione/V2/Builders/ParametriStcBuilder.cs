using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriStcBuilder : AreaRiservataWebConfigBuilder,IConfigurazioneBuilder<ParametriStc>
	{
		private static class Constants
		{
			public const string NomeComuneDefault = "default";
		}

		IAliasSoftwareResolver _aliasResolver;
        IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;


        public ParametriStcBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
		{
			this._aliasResolver = aliasResolver;
            this._configurazioneAreaRiservataRepository = configurazioneAreaRiservataRepository;
		}


		#region IBuilder<ParametriStc> Members

		public ParametriStc Build()
		{
			var cfg = GetConfigurazioneStc();
            var wsCfg = GetWsConfig();

			var nodoMittente = new NodoStc( cfg.IdNodoMittente , cfg.IdEnteMittente , cfg.IdSportelloMittente );
			var nodoDestinatario = new NodoStc( cfg.IdNodoDestinatario , cfg.IdEnteDestinatario, cfg.IdSportelloDestinatario);
            var includiTecnicoInSoggettiCollegati = wsCfg.TecnicoInSoggettiCollegati;

            return new ParametriStc(cfg.UrlInvio, cfg.Username, cfg.Password, nodoMittente, nodoDestinatario, includiTecnicoInSoggettiCollegati);
		}

		#endregion


		private ConfigurazioneStc GetConfigurazioneStcPerComune()
		{
			string idComune = _aliasResolver.AliasComune;

			var cfg = GetConfigurazione();

			if (cfg.ParametriStc[idComune] != null)
				return cfg.ParametriStc[idComune];

			return cfg.ParametriStc[Constants.NomeComuneDefault];
		}


		public ConfigurazioneStc GetConfigurazioneStc()
		{
			var software = _aliasResolver.Software;
			var cfg		 = GetConfigurazioneStcPerComune();

			var specializzazioni = cfg.Specializzazioni[software];

			var rVal = new ConfigurazioneStc
			{
				IdComune = cfg.IdComune,

				IdNodoMittente = cfg.IdNodoMittente,
				IdEnteMittente = cfg.IdEnteMittente,
				IdSportelloMittente = cfg.IdSportelloMittente,

				IdNodoDestinatario = cfg.IdNodoDestinatario,
				IdEnteDestinatario = cfg.IdEnteDestinatario,
				IdSportelloDestinatario = cfg.IdSportelloDestinatario,

				UrlInvio = cfg.UrlInvio,

				Username = cfg.Username,
				Password = cfg.Password
			};

			if (specializzazioni != null)
			{
				// dati del nodo mittente
				if (!String.IsNullOrEmpty(specializzazioni.IdNodoMittente))
					rVal.IdNodoMittente = specializzazioni.IdNodoMittente;

				if (!String.IsNullOrEmpty(specializzazioni.IdEnteMittente))
					rVal.IdEnteMittente = specializzazioni.IdEnteMittente;

				if (!String.IsNullOrEmpty(specializzazioni.IdSportelloMittente))
					rVal.IdSportelloMittente = specializzazioni.IdSportelloMittente;

				// dati del nodo destinatario
				if (!String.IsNullOrEmpty(specializzazioni.IdNodoDestinatario))
					rVal.IdNodoDestinatario = specializzazioni.IdNodoDestinatario;

				if (!String.IsNullOrEmpty(specializzazioni.IdEnteDestinatario))
					rVal.IdEnteDestinatario = specializzazioni.IdEnteDestinatario;

				if (!String.IsNullOrEmpty(specializzazioni.IdSportelloDestinatario))
					rVal.IdSportelloDestinatario = specializzazioni.IdSportelloDestinatario;


				// Username e password
				if (!String.IsNullOrEmpty(specializzazioni.Username))
					rVal.Username = specializzazioni.Username;

				if (!String.IsNullOrEmpty(specializzazioni.Password))
					rVal.Password = specializzazioni.Password;
			}

			rVal.IdNodoMittente = ReplaceIdComuneSoftware(rVal.IdNodoMittente);
			rVal.IdEnteMittente = ReplaceIdComuneSoftware(rVal.IdEnteMittente);
			rVal.IdSportelloMittente = ReplaceIdComuneSoftware(rVal.IdSportelloMittente);

			rVal.IdNodoDestinatario = ReplaceIdComuneSoftware(rVal.IdNodoDestinatario);
			rVal.IdEnteDestinatario = ReplaceIdComuneSoftware(rVal.IdEnteDestinatario);
			rVal.IdSportelloDestinatario = ReplaceIdComuneSoftware(rVal.IdSportelloDestinatario);

			rVal.Username = ReplaceIdComuneSoftware(rVal.Username);
			rVal.Password = ReplaceIdComuneSoftware(rVal.Password);

			return rVal;
		}

		private string ReplaceIdComuneSoftware(string parametro)
		{
			string idComune = _aliasResolver.AliasComune;
			string software = _aliasResolver.Software;

			var str = Regex.Replace(parametro, "\\{[Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee]\\}", idComune);
			str = Regex.Replace(str, "\\{[Ss][Oo][Ff][Tt][Ww][Aa][Rr][Ee]\\}", software);

			return str;
		}

        protected ConfigurazioneAreaRiservataDto GetWsConfig()
        {
            return this._configurazioneAreaRiservataRepository.DatiConfigurazione(this._aliasResolver.AliasComune, this._aliasResolver.Software);
        }

	}
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	internal enum ModalitaInvioIstanzaEnum
	{
		Sigepro,
		STC
	}

	public class ConfigurazioneWebConfig
	{
		public ConfigurazioneSigeproSecurity SigeproSecurity
		{
			get
			{
				var cfg = GetConfigurazione();

				return cfg.SigeproSecurity;
			}
		}

		internal ModalitaInvioIstanzaEnum ModalitaInvio
		{
			get 
			{
				var cfg = GetConfigurazione();

				try
				{
					return (ModalitaInvioIstanzaEnum)Enum.Parse(typeof(ModalitaInvioIstanzaEnum), cfg.ModalitaInvio, true);
				}
				catch (Exception /*ex*/)
				{
					return ModalitaInvioIstanzaEnum.Sigepro;
				}
			}
		}

		public string GetProcessFileName(string idComune, string software)
		{
			var cfg = GetParametriLocalizzati(idComune);

			if (String.IsNullOrEmpty(cfg.ProcessFile.Default))
				cfg = GetParametriLocalizzati("default");

			var spec = cfg.ProcessFile.Specializzazioni[software];

			if (spec == null)
				return cfg.ProcessFile.Default;

			var processFile = spec.File;

			if (String.IsNullOrEmpty(processFile))
				return cfg.ProcessFile.Default;

			return processFile;
		}

		public ConfigurazioneLocalizzata GetParametriLocalizzati(string idComune)
		{
			var cfg = GetConfigurazione();

			if (cfg.ParametriPerIdComune[idComune] != null)
				return cfg.ParametriPerIdComune[idComune];

			return cfg.ParametriPerIdComune["default"];
		}

		public string GetUrlInvioStc(string aliasComune)
		{
			var cfg = GetConfigurazioneStcPerComune(aliasComune);

			return cfg.UrlInvio;
		}

		public string GetUsernameStc(string aliasComune)
		{
			var cfg = GetConfigurazioneStcPerComune(aliasComune);

			return cfg.Username;
		}

		public string GetPasswordStc(string aliasComune)
		{
			var cfg = GetConfigurazioneStcPerComune(aliasComune);

			return cfg.Password;
		}

		public ConfigurazioneStc GetConfigurazioneStc(string idComune, string software)
		{
			var cfg = GetConfigurazioneStcPerComune(idComune);

			var specializzazioni = cfg.Specializzazioni[software];

			var rVal = new ConfigurazioneStc{
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

			rVal.IdNodoMittente = ReplaceIdComuneSoftware(rVal.IdNodoMittente, idComune,software);
			rVal.IdEnteMittente = ReplaceIdComuneSoftware(rVal.IdEnteMittente, idComune, software);
			rVal.IdSportelloMittente = ReplaceIdComuneSoftware(rVal.IdSportelloMittente, idComune, software);

			rVal.IdNodoDestinatario = ReplaceIdComuneSoftware(rVal.IdNodoDestinatario, idComune, software);
			rVal.IdEnteDestinatario = ReplaceIdComuneSoftware(rVal.IdEnteDestinatario, idComune, software);
			rVal.IdSportelloDestinatario = ReplaceIdComuneSoftware(rVal.IdSportelloDestinatario, idComune, software);
			
			rVal.Username = ReplaceIdComuneSoftware(rVal.Username, idComune, software);
			rVal.Password = ReplaceIdComuneSoftware(rVal.Password, idComune, software);

			return rVal;
		}

		private string ReplaceIdComuneSoftware(string parametro, string idComune, string software)
		{
			var str = Regex.Replace(parametro, "\\{[Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee]\\}", idComune);
			str = Regex.Replace(str, "\\{[Ss][Oo][Ff][Tt][Ww][Aa][Rr][Ee]\\}", software);

			return str;
		}


		private ConfigurazioneStc GetConfigurazioneStcPerComune(string idComune)
		{
			var cfg = GetConfigurazione();

			if (cfg.ParametriStc[idComune] != null)
				return cfg.ParametriStc[idComune];

			return cfg.ParametriStc["default"];
		}

		private ConfigurazioneFrontEndWebConfig GetConfigurazione()
		{
			ConfigurazioneFrontEndWebConfig config = (ConfigurazioneFrontEndWebConfig)System.Configuration.ConfigurationManager.GetSection("sigepro/frontEnd");

			return config;
		}
	}
}

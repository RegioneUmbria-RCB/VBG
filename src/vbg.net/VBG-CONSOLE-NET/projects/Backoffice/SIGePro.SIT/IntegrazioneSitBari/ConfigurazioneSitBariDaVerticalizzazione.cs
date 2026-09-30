using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Verticalizzazioni;
using log4net;

namespace Init.SIGePro.Sit.IntegrazioneSitBari
{
	public class ConfigurazioneSitBariDaVerticalizzazione : IConfigurazioneSitBari
	{
		private static class Constants
		{
			public const string DefaultCodEnte = "01";
			public const string DefaultrequestFrom = "Sigepro";
			public const string DefaultTipoindirizzoCercato = "c";
		}

		ILog _log = LogManager.GetLogger(typeof(ConfigurazioneSitBariDaVerticalizzazione));

		string _codEnte = Constants.DefaultCodEnte;
		string _requestFrom = Constants.DefaultrequestFrom;
		string _tipoIndirizzoCercato = Constants.DefaultTipoindirizzoCercato;

		public ConfigurazioneSitBariDaVerticalizzazione(VerticalizzazioneSitBari verticalizzazione)
		{
			_log.DebugFormat("Parametri di configurazione di sit nautilus bari dalla verticalizzazione: codEnte={0}, requestForm={1}, tipoindirizzoCercato={2}",
								verticalizzazione.CodEnte,
								verticalizzazione.RequestFrom,
								verticalizzazione.TipoIndirizzoCercato);

			if (!String.IsNullOrEmpty(verticalizzazione.CodEnte))
				this._codEnte = verticalizzazione.CodEnte;

			if (!String.IsNullOrEmpty(verticalizzazione.RequestFrom))
				this._requestFrom = verticalizzazione.RequestFrom;

			if (!String.IsNullOrEmpty(verticalizzazione.TipoIndirizzoCercato))
				this._tipoIndirizzoCercato = verticalizzazione.TipoIndirizzoCercato;

			_log.DebugFormat("Parametri di configurazione di sit nautilus bari in uso: codEnte={0}, requestForm={1}, tipoindirizzoCercato={2}",
								this._codEnte,
								this._requestFrom,
								this._tipoIndirizzoCercato);
		}

		public string CodEnte
		{
			get { return this._codEnte; }
		}

		public string RequestFrom
		{
			get { return this._requestFrom; }
		}

		public string TipoIndirizzoRicercato
		{
			get { return this._tipoIndirizzoCercato; }
		}
	}
}

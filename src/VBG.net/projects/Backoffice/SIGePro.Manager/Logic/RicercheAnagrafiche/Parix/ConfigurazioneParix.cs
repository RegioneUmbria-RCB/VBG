// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneParix.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using log4net;
	using PersonalLib2.Data;
	using Init.SIGePro.Verticalizzazioni;
	using Init.SIGePro.Exceptions.Protocollo;
	using System.Configuration;

	internal class ConfigurazioneParix
	{
		ILog _log = LogManager.GetLogger(typeof(ConfigurazioneParix));

		public class ParametriInizializzazione
		{
			public string IdComune { get; set; }
            public string IdComuneAlias { get; set; }
			public DataBase Database { get; set; }
		}
		Func<ParametriInizializzazione, ParametriInizializzazione> _initFunc;


		internal ConfigurazioneParix(Func<ParametriInizializzazione, ParametriInizializzazione> initFunc)
		{
			this._initFunc = initFunc;
		}

		VerticalizzazioneWsanagrafeParix _verticalizzazioneParix;

		public VerticalizzazioneWsanagrafeParix Get
		{
			get
			{
				if (_verticalizzazioneParix == null)
				{
					_verticalizzazioneParix = GetVerticalizzazioneWsAnagrafeParix();
					//_xmlValidator = new ParixXmlValidator(_verticalizzazioneParix.Xsd);
				}

				return _verticalizzazioneParix;
			}
		}

		public bool IsVerticalizzazioneAttiva
		{
			get
			{
				try
				{
					var parametri = this._initFunc(new ParametriInizializzazione());

					var vert = new VerticalizzazioneWsanagrafeParix(parametri.IdComuneAlias, "TT");

					return vert.Attiva;
				}
				catch (Exception ex)
				{
					var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
					_log.Error(errore);
					throw new Exception(errore, ex);
				}
			}
		}

		private VerticalizzazioneWsanagrafeParix GetVerticalizzazioneWsAnagrafeParix()
		{
			try
			{
				var parametri = this._initFunc(new ParametriInizializzazione());

				var vert = new VerticalizzazioneWsanagrafeParix(parametri.IdComuneAlias, "TT");

				if (!vert.Attiva)
					throw new ConfigurationException("La verticalizzazione WSANAGRAFE_PARIX non è attiva.\r\n");

				return vert;
			}
			catch (Exception ex)
			{
				var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
				_log.Error(errore);
				throw new Exception(errore, ex);
			}
		}
	}
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriVisuraBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriVisura>
	{
		private static class Constants
		{
			public const string ConfigKeyName = "limiteRecordsArchivioPratiche";
		}



		int _limiteRecordsArchivioPratiche = 200;

		public ParametriVisuraBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repository)
			:base(aliasResolver,repository )
		{
			var cfgValue = ConfigurationManager.AppSettings[Constants.ConfigKeyName];

			if (!String.IsNullOrEmpty(cfgValue))
				this._limiteRecordsArchivioPratiche = int.Parse(cfgValue);
		}


		#region IBuilder<ParametriVisura> Members

		public ParametriVisura Build()
		{
			var cfg = GetConfig();



			return new ParametriVisura(
				this._limiteRecordsArchivioPratiche, 
				cfg.IntestazioneDettaglioVisura,
				new ParametriVisura.ParametriRicerca( 
						cfg.ParametriRicercaVisuraTecnico.CercaComeTecnico,
						cfg.ParametriRicercaVisuraTecnico.CercaComeRichiedente,
						cfg.ParametriRicercaVisuraTecnico.CercaComeAzienda,
						cfg.ParametriRicercaVisuraTecnico.CercaPartitaIva,
						cfg.ParametriRicercaVisuraTecnico.CercaSoggettiCollegati
					),
				new ParametriVisura.ParametriRicerca( 
						cfg.ParametriRicercaVisuraNonTecnico.CercaComeTecnico,
						cfg.ParametriRicercaVisuraNonTecnico.CercaComeRichiedente,
						cfg.ParametriRicercaVisuraNonTecnico.CercaComeAzienda,
						cfg.ParametriRicercaVisuraNonTecnico.CercaPartitaIva,
						cfg.ParametriRicercaVisuraNonTecnico.CercaSoggettiCollegati
					),
				new ParametriVisura.ParametriRicerca(
						cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeTecnico,
						cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeRichiedente,
						cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeAzienda,
						cfg.ParametriRicercaVisuraFiltroRichiedente.CercaPartitaIva,
						cfg.ParametriRicercaVisuraFiltroRichiedente.CercaSoggettiCollegati
					),
				new ParametriVisura.ParametriVisuramobile(
					cfg.ParametriVisuraMobile.UrlServizioProfili,
					cfg.ParametriVisuraMobile.AliasSportello
					),

                new ParametriVisura.ParametriDettaglioPratica(cfg.DettaglioVisura.NascondiStatoIstanza, cfg.DettaglioVisura.NascondiResponsabili),

                cfg.ArpaCalabria
			);
		}

		#endregion
	}
}

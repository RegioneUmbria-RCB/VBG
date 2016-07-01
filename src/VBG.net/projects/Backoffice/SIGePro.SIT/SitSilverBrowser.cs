// -----------------------------------------------------------------------
// <copyright file="SitSilverBrowser.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Sit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Sit.Manager;
	using Init.SIGePro.Verticalizzazioni;
	using Init.SIGePro.Manager.Verticalizzazioni;
	using Init.SIGePro.Sit.SilverBrowser;
	using Init.SIGePro.Sit.ValidazioneFormale;
	using Init.SIGePro.Sit.Data;
	using log4net;
	using CuttingEdge.Conditions;
	using Init.SIGePro.Sit.Errors;
	using Init.SIGePro.Manager.DTO;


	public static class RisultatiExtensions
	{
		public static IEnumerable<string> SeContiene(this IEnumerable<string> risultati, string partial)
		{
			if (String.IsNullOrEmpty(partial.Trim()))
				return risultati;

			return risultati.Where(x => x.ToUpperInvariant().Contains(partial.ToUpperInvariant()));
		}
	}

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SIT_SILVERBROWSER: SitBaseV2
	{



		SilverBrowserClient _client;
		string _urlZoomDaPunto;
		string _urlZoomDaParticella;
		string _urlZoomDaPuntoBo;
		string _urlZoomDaParticellaBo;
		ILog _log = LogManager.GetLogger(typeof(SIT_SILVERBROWSER));

		public SIT_SILVERBROWSER(): base(new ValidazioneFormaleTramiteCodiceCivicoService() )
		{
		}

		public override RetSit ElencoCivici()
		{
			var codiceVia = this.DataSit.CodVia;

			Condition.Requires(codiceVia).IsNotEmpty();

			try
			{
				var civici = this._client.ListaCivici(codiceVia).Select(x => x.numero).SeContiene(this.DataSit.Civico);

				return new RetSit(true, civici);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoCivici -> errore durante la lettura con i parametri codiceVia={0}: {1}", codiceVia, ex.ToString());

				throw;
			}
		}

		public override RetSit ElencoEsponenti()
		{
			var codiceVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;

			Condition.Requires(codiceVia).IsNotEmpty();
			Condition.Requires(civico).IsNotEmpty();

			try
			{
				var esponenti = this._client.ListaEsponenti(codiceVia, civico).Select(x => x.esponente).SeContiene(this.DataSit.Esponente);

				return new RetSit(true, esponenti);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoEsponenti -> errore durante la lettura con i parametri codiceVia={0}, civico={1}: {2}", codiceVia, civico, ex.ToString());

				throw;
			}
		}

		public override RetSit ElencoSezioni()
		{
			try
			{
				var sezioni = this._client.ListaSezioni().Select(x => x.sez).SeContiene(this.DataSit.Sezione); ;

				return new RetSit(true, sezioni);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER->ElencoSezioni: errore durante la lettura {0}", ex.ToString());

				throw;
			}			
		}

		public override RetSit ElencoFogli()
		{
			try
			{
				var sezioni = this._client.ListaFogli().Select(x => x.foglio).SeContiene(this.DataSit.Foglio); ;

				return new RetSit(true, sezioni);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER->ElencoFogli: errore durante la lettura {0}", ex.ToString());

				throw;
			}				
		}

		public override RetSit ElencoParticelle()
		{
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;

			Condition.Requires(foglio).IsNotEmpty();

			try
			{
				var particelle = this._client.ListaParticelle(sezione, foglio).Select(x => x.numero).SeContiene(this.DataSit.Particella); ;

				return new RetSit(true, particelle);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoParticelle -> errore durante la lettura con i parametri sezione={0}, foglio={1}: {2}", sezione, foglio, ex.ToString());

				throw;
			}			
		}

		public override RetSit ElencoSub()
		{
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;
			var particella = this.DataSit.Particella;

			// Condition.Requires(sezione).IsNotEmpty();
			Condition.Requires(foglio).IsNotEmpty();
			Condition.Requires(particella).IsNotEmpty();

			try
			{
				var sub = this._client.ListaSub(sezione, foglio, particella).Select(x => x.sub).SeContiene(this.DataSit.Particella); ;

				return new RetSit(true, sub);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoSub -> errore durante la lettura con i parametri sezione={0}, foglio={1}, particella={2}: {3}", sezione, foglio, particella, ex.ToString());

				throw;
			}					
		}

		public override RetSit CivicoValidazione()
		{
			return EsponenteValidazione();
		}

		public override RetSit EsponenteValidazione()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;

			if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
			{
				return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "codice via o civico non valorizzati");
			}

			try
			{
				var datiCivico = String.IsNullOrEmpty(esponente) ? this._client.VerificaCivico(codVia, civico) : this._client.VerificaCivicoConEsponente(codVia, civico, esponente);

				if (datiCivico == null)
				{
					var errMsg = String.Format("Impossibile validare il civico {0}{1} nella via {2}", civico, esponente, codVia);

					return new ErrorMessage(MessageCode.CivicoValidazione).ToRetSit(false, errMsg);
				}

				this.DataSit.ExtendWith(datiCivico.ToDatiLocalizzazione());

				return new RetSit(true);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore durante la validazione del civico: codiceVia={0}, civico={1}, esponente={3}: {4}", codVia, civico, esponente, ex.ToString());

				throw;
			}
		}

		

		public override RetSit ParticellaValidazione()
		{
			var foglio = this.DataSit.Foglio;
			var particella = this.DataSit.Particella;


			if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
			{
				return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "foglio o particella non valorizzati");
			}

			try
			{
				var datiParticella = this._client.VerificaParticella(foglio, particella);

				if (datiParticella == null)
				{
					var errMsg = String.Format("Impossibile validare la particella {0} (foglio: {1})", foglio, particella);

					return new ErrorMessage(MessageCode.ParticellaValidazione).ToRetSit(false, errMsg);
				}

				this.DataSit.ExtendWith(datiParticella.ToDatiLocalizzazione());

				return new RetSit(true);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore durante la validazione della particella: foglio={0}, particella={1}: {3}", foglio, particella, ex.ToString());

				throw;
			}			
		}

		public override RetSit SubValidazione()
		{
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;
			var particella = this.DataSit.Particella;
			var sub = this.DataSit.Sub;


			if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella) || String.IsNullOrEmpty(sub))
			{
				return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "foglio, particella o sub non valorizzati");
			}

			try
			{
				var datiSub = this._client.VerificaSub(sezione, foglio, particella, sub);

				if (datiSub == null)
				{
					var errMsg = String.Format("Impossibile validare il sub {0} (sezione: {0}, foglio: {1}, particella: {2})", sub, sezione, foglio, particella);

					return new ErrorMessage(MessageCode.ParticellaValidazione).ToRetSit(false, errMsg);
				}

				this.DataSit.ExtendWith(datiSub.ToDatiLocalizzazione());

				return new RetSit(true);
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore durante la validazione della particella: foglio={0}, particella={1}: {3}", foglio, particella, ex.ToString());

				throw;
			}	
		}

		

		public override string[] GetListaCampiGestiti()
		{
			return new[]
			{
				SitIntegrationService.NomiCampiSit.Civico,
				SitIntegrationService.NomiCampiSit.Esponente,
				SitIntegrationService.NomiCampiSit.Sezione,
				SitIntegrationService.NomiCampiSit.Foglio,
				SitIntegrationService.NomiCampiSit.Particella,
				SitIntegrationService.NomiCampiSit.Sub,				
				SitIntegrationService.NomiCampiSit.CodiceCivico,
				SitIntegrationService.NomiCampiSit.CodiceVia/*,
				SitIntegrationService.NomiCampiSit.Coordinate*/
			};
		}

		public override void SetupVerticalizzazione()
		{
			var verticalizzazione = new VerticalizzazioneSitSilverBrowser(this.Alias, this.Software);

			if (!verticalizzazione.Attiva)
			{
				throw new Exception(String.Format("La verticalizzazione SIT_SILVERBROWSER non è attiva per l'alias {0} e il software {1}", this.Alias, this.Software));
			}

			var url = verticalizzazione.ServiceBaseUrl;

			if (!url.EndsWith("/"))
				url += "/";

			this._client = new SilverBrowserClient(url);
			this._urlZoomDaPunto = verticalizzazione.MapZoomDaPunto;
			this._urlZoomDaParticella = verticalizzazione.MapZoomDaParticella;
			this._urlZoomDaPuntoBo = verticalizzazione.MapZoomDaPuntoBo;
			this._urlZoomDaParticellaBo = verticalizzazione.MapZoomDaParticellaBo;

		}

		public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
		{
			return new[] {
				new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPunto),
				new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaParticella)
			};
		}

		public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
		{
			return new[] {
				new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPuntoBo),
				new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaParticellaBo)
			};
		}
	}
}

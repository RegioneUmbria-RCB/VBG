using System;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.IntegrazioneSitBari;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Sit
{
	public class SIT_BARI : SitBase
	{
		private static class Constants
		{
			public const string ValidazioneCivico_ErroreCodiceViaNonImpostatoFmtStr = "Impossibile validare il civico {0}, codice via non impostato";
			public const string ValidazioneCivico_ErroreCivicoNonImpostatoFmtStr = "Validazione civico fallita, civico non impostato";
			public const string ValidazioneCivico_ErroreCivicoNonValidoFmtStr = "Validazione civico fallita, il civico {0} non è un valore numerico";
			public const string ValidazioneCivico_ErroreCivicoNonTrovato = "Validazione civico fallita, il civico {0} non esiste nel sistema SIT in uso";
		}

		public SIT_BARI():base( new ValidazioneFormaleTramiteCodiceCivicoService() )
		{

		}

		private VerticalizzazioneSitBari GetVerticalizzazione()
		{
			var vert = new VerticalizzazioneSitBari(this.IdComuneAlias, this.Software);

			if (vert == null || !vert.Attiva)
				throw new InvalidOperationException("La verticalizzazione SIT_BARI non è attiva");

			return vert;
		}

		protected virtual ISitNautilusBariService CreaServizioSit()
		{
			var verticalizzazione = GetVerticalizzazione();

			var configurazioneSit = new ConfigurazioneSitBariDaVerticalizzazione( verticalizzazione );
			var serviceAdapter = new RicercaCivicoSEIAdapter( verticalizzazione );

			return new SitNautilusBariService(configurazioneSit, serviceAdapter);
		}

		public override RetSit CivicoValidazione()
		{
			if (String.IsNullOrEmpty(this.DataSit.CodVia))
				return RestituisciErroreSit(String.Format(Constants.ValidazioneCivico_ErroreCodiceViaNonImpostatoFmtStr, this.DataSit.Civico), MessageCode.CivicoValidazione, false);

			if (String.IsNullOrEmpty(this.DataSit.Civico))
				return RestituisciErroreSit(Constants.ValidazioneCivico_ErroreCivicoNonImpostatoFmtStr, MessageCode.CivicoValidazione, false);

			return InvocaWebServiceValidazione(this.DataSit.CodVia, this.DataSit.Civico, this.DataSit.Esponente);
		}

		public override RetSit EsponenteValidazione()
		{
			return CivicoValidazione();
		}

		private RetSit InvocaWebServiceValidazione(string codVia, string strCivico, string esponente)
		{
			uint civico = 0;

			if (!UInt32.TryParse(strCivico, out civico))
				return RestituisciErroreSit(String.Format(Constants.ValidazioneCivico_ErroreCivicoNonValidoFmtStr, strCivico), MessageCode.CivicoValidazione, false);

			var servizioSit = CreaServizioSit();

			var esitoVerifica = servizioSit.VerificaEsistenzaIndirizzo(codVia, civico, esponente);

			if (esitoVerifica == null)
			{
				var errCivico = String.IsNullOrEmpty(esponente) ? strCivico : String.Format("{0}/{1}", strCivico, esponente);
				return RestituisciErroreSit(String.Format(Constants.ValidazioneCivico_ErroreCivicoNonTrovato, errCivico), MessageCode.CivicoValidazione, false);
			}

			this.DataSit.Circoscrizione = esitoVerifica.Circoscrizione;
			this.DataSit.Frazione = esitoVerifica.Localita;
			this.DataSit.CodCivico = esitoVerifica.CodCivico;// String.Format("{0}${1}${2}", codVia, strCivico, esponente);

			return new RetSit(true );
		}

		#region metodi astratti (non implementati)

		protected override string GetFoglio()
		{
			throw new NotImplementedException();
		}

		protected override string GetParticella()
		{
			throw new NotImplementedException();
		}

		protected override RetSit VerificaFoglio()
		{
			throw new NotImplementedException();
		}

		protected override RetSit VerificaParticella()
		{
			throw new NotImplementedException();
		}

		protected override string GetCodVia()
		{
			throw new NotImplementedException();
		}

		#endregion

		public override void SetupVerticalizzazione()
		{
			// ...
		}

		public override string[] GetListaCampiGestiti()
		{
			return new String[]{
				SitIntegrationService.NomiCampiSit.Civico,
				SitIntegrationService.NomiCampiSit.Esponente
			};
		}
	}
}

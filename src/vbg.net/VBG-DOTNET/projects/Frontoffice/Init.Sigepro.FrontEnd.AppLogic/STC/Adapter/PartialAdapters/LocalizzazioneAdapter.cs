using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class LocalizzazioneAdapter : IStcPartialAdapter
	{
		private static class Constants
		{
			public const string NoteStradario = "$NOTE_ISTANZESTRADARIO$-";
			public const string CodiceCivicoStradario = "$CODCIVICO_ISTANZESTRADARIO$-";
		}

		ParametriHelper _parametriHelper = new ParametriHelper();

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			_dettaglioPratica.localizzazione = _readInterface.Localizzazioni
													.Indirizzi
													.Select(x => x.ToLocalizzazioneNelComuneType())
													.ToArray();

			// Valori aggiunti su altri dati

			var listaAltriDati = new List<ParametroType>();

			if (_dettaglioPratica.altriDati != null)
				listaAltriDati.AddRange(_dettaglioPratica.altriDati); 


			
			listaAltriDati.AddRange(_readInterface
										.Localizzazioni
										.Indirizzi
										.Select((localizzazione, idx) => new { nomeParametro = Constants.NoteStradario + idx.ToString(), valore = localizzazione.Note })
										.Where(x => !String.IsNullOrEmpty(x.valore))
										.Select(x => _parametriHelper.CreaParametroType(x.nomeParametro, x.valore)));

			listaAltriDati.AddRange(_readInterface
							.Localizzazioni
							.Indirizzi
							.Select((localizzazione, idx) => new { nomeParametro = Constants.CodiceCivicoStradario + idx.ToString(), valore = localizzazione.CodiceCivico })
							.Where(x => !String.IsNullOrEmpty(x.valore))
							.Select(x => _parametriHelper.CreaParametroType(x.nomeParametro, x.valore)));

			_dettaglioPratica.altriDati = listaAltriDati.ToArray();
		}
	}
}

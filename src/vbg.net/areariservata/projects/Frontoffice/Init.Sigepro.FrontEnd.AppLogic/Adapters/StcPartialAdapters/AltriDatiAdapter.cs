using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class AltriDatiAdapter : IStcPartialAdapter
	{
		private static class Constants
		{
			public static class NomiTagsAltriDati
			{
				public const string CodiceAutorizzazione = "Autorizzazioni.Codice";
				public const string NumeroAutorizzazione = "Autorizzazioni.Numero";
				public const string DataAutorizzazione = "Autorizzazioni.Data";
				//public const string CodiceEnteAutorizzazione = "Autorizzazioni.Ente.Codice";
				public const string DescrizioneEnteAutorizzazione = "Autorizzazioni.Ente.Descrizione";
				public const string NumeroPresenzeCalcolateAutorizzazione = "Autorizzazioni.NumeroPresenze.Calcolate";
				public const string NumeroPresenzeDichiarateAutorizzazione = "Autorizzazioni.NumeroPresenze.Dichiarate";
				//public const string DatiUtenzaTaresBari = "Bari.Tares.DatiUtenza";
				public const string Eventi = "AREARISERVATA_EVENTI";
				public const string IdentificativoSuap = "IDENTIFICATIVO_SUAP";
				public const string IdDomandaCollegata = "#CERCA_PRATICA_COLLEGATA_PADRE#";
            }
		}

		ParametriHelper _parametriHelper = new ParametriHelper();
		ICodiceAccreditamentoHelper _codiceAccreditamentoHelper;

		internal AltriDatiAdapter(ICodiceAccreditamentoHelper codiceAccreditamentoHelper)
		{
			this._codiceAccreditamentoHelper = codiceAccreditamentoHelper;
		}

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var listaAltriDati = new List<ParametroType>();

			if (_dettaglioPratica.altriDati != null)
				listaAltriDati.AddRange(_dettaglioPratica.altriDati);

			// EVENTI DELL'ISTANZA
			// Gli eventi dell'istanza generati nel FO vengono aggiunti nella sezione AltriDati

			if (_readInterface.AltriDati.Eventi.Count() > 0)
			{
				var parametroEventi = new ParametroType
				{
					nome = Constants.NomiTagsAltriDati.Eventi,
					valore = _readInterface.AltriDati.Eventi
													 .Select(evento => new ValoreParametroType
													 {
														 codice = evento.Codice,
														 descrizione = evento.Descrizione
													 })
														.ToArray()
				};

				listaAltriDati.Add(parametroEventi);
			}

			// CODICE ACCREDITAMENTO
			// Il codice accreditamento dello sportello viene aggiunto nella sezione "altriDati"
			var codiceAccreditamento = this._codiceAccreditamentoHelper.GetCodiceAccreditamento();

			if (!String.IsNullOrEmpty(codiceAccreditamento))
			{
				listaAltriDati.Add(_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.IdentificativoSuap, codiceAccreditamento));
			}

            if (_readInterface.AltriDati.IdDomandaCollegata.HasValue)
            {
                listaAltriDati.Add(_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.IdDomandaCollegata, _readInterface.AltriDati.IdDomandaCollegata.Value.ToString()));
            }

			// Dati autorizzazioni
			if (_readInterface.AutorizzazioniMercati.Autorizzazione != null)
			{
				var aut = _readInterface.AutorizzazioniMercati.Autorizzazione;

				listaAltriDati.AddRange(
					new ParametroType[]{
						_parametriHelper.CreaParametroType( Constants.NomiTagsAltriDati.CodiceAutorizzazione, aut.Id.ToString()),
						_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroAutorizzazione, aut.Numero),
						_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.DataAutorizzazione, aut.Data),
						_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.DescrizioneEnteAutorizzazione, aut.EnteRilascio.Descrizione),
						_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroPresenzeCalcolateAutorizzazione, aut.NumeroPresenzeCalcolato),
						_parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroPresenzeDichiarateAutorizzazione, aut.NumeroPresenzeDichiarato)
				});
			}
			
			// Valori inseriti nella sezione "dati extra"
			foreach (var key in _readInterface.DatiExtra.Keys)
			{
				var value = _readInterface.DatiExtra.GetValoreDato(key);
				listaAltriDati.Add( _parametriHelper.CreaParametroType(key, value,String.Empty));
			}

			_dettaglioPratica.altriDati = listaAltriDati.ToArray();
		}
	}
}

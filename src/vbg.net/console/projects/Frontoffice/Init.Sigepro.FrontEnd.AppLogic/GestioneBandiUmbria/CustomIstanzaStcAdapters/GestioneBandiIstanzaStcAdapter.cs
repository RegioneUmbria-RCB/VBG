// -----------------------------------------------------------------------
// <copyright file="GestioneBandiIstanzaStcAdapter.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.CustomIstanzaStcAdapters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
	using Init.Sigepro.FrontEnd.AppLogic.Adapters;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class GestioneBandiIstanzaStcAdapter : IIstanzaStcAdapter
	{
		string _partitaIvaAzienda;
		string _codiceFiscaleAzienda;
		IIstanzaStcAdapter _istanzaStcAdapter;
		AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

		public GestioneBandiIstanzaStcAdapter(string partitaIvaAzienda, string codiceFiscaleAzienda, IIstanzaStcAdapter stcAdapter)
		{
			this._partitaIvaAzienda = partitaIvaAzienda;
			this._codiceFiscaleAzienda = codiceFiscaleAzienda;
			this._istanzaStcAdapter = stcAdapter;
		}



		public DettaglioPraticaType Adatta(DomandaOnline domanda)
		{
			var domandaStc = this._istanzaStcAdapter.Adatta(domanda);

			domandaStc.richiedente = null;
			domandaStc.aziendaRichiedente = null;
			domandaStc.altriSoggetti = null;

			var nuovoRichiedente = domanda
									.ReadInterface
									.Anagrafiche
									.Anagrafiche
									.Where(x => x.AnagraficaCollegata != null && x.AnagraficaCollegata.PartitaIva == this._partitaIvaAzienda && x.AnagraficaCollegata.Codicefiscale == this._codiceFiscaleAzienda)
									.FirstOrDefault();

			if (nuovoRichiedente != null)
			{
				domandaStc.richiedente = new RichiedenteType
				{
					ruolo = _anagraficheHelper.AdattaRuolo(nuovoRichiedente),
					anagrafica = _anagraficheHelper.AdattaPersonaFisica(nuovoRichiedente)
				};

				var nuovaAzienda = nuovoRichiedente.AnagraficaCollegata;

				if (nuovaAzienda != null)
				{
					domandaStc.aziendaRichiedente = _anagraficheHelper.AdattaPersonaGiuridica(nuovaAzienda, null, nuovoRichiedente);
				}
			}

			return domandaStc;
		}
	}
}

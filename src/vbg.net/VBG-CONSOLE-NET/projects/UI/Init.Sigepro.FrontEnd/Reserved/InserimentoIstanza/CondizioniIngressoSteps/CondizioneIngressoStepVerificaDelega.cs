using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps
{
	public class CondizioneIngressoStepVerificaDelega : ICondizioneIngressoStep
	{
		bool _ignoraRichiestaDelegaSeProcuratoreDelRichiedente;
		IReadFacade _readFacade;


		public CondizioneIngressoStepVerificaDelega(IReadFacade readFacade, bool ignoraRichiestaDelegaSeProcuratoreDelRichiedente)
		{
			this._readFacade = readFacade;
			this._ignoraRichiestaDelegaSeProcuratoreDelRichiedente = ignoraRichiestaDelegaSeProcuratoreDelRichiedente;
		}

		#region ICondizioneIngressoStep Members

		public bool Verificata()
		{
			var codiceFiscaleUtente = _readFacade.DomandaDataKey.CodiceUtente.ToUpperInvariant();

			// Se l'utente che sta presentando la domanda è uno dei richiedenti la delega non serve
			var count = _readFacade.Domanda.Anagrafiche.GetRichiedenti()
						.Where(x => x.Codicefiscale.ToUpperInvariant() == codiceFiscaleUtente )
						.Count();

			if (count != 0)
				return false;

			// Nel caso in cui il flag sia impostato e l'utente che presenta la pratica non è uno dei richiedenti
			// Allora verifico se l'utente è uno dei procuratori dei richiedenti
			if (this._ignoraRichiestaDelegaSeProcuratoreDelRichiedente)
			{
				var utenteIsProcuratoreDelRichiedente = _readFacade.Domanda
																   .Anagrafiche
																   .GetRichiedenti()
																   .Where(x => _readFacade.Domanda.Procure.GetCodiceFiscaleDelProcuratoreDi(x.Codicefiscale) == codiceFiscaleUtente)
																   .Count() > 0;

				if(utenteIsProcuratoreDelRichiedente)
					return false;
			}

			return true;
		}

		#endregion
	}
}
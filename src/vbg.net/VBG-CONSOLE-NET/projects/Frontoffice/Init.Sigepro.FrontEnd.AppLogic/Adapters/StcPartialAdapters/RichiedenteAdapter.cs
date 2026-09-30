using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class RichiedenteAdapter : IStcPartialAdapter
	{
		AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var richiedente = _readInterface.Anagrafiche.GetRichiedente();

			if (richiedente == null)
				throw new Exception("Impossibile ricavare il richiedente della domanda");

			var datiRichiedente = new RichiedenteType
			{
				ruolo = _anagraficheHelper.AdattaRuolo(richiedente),
				anagrafica = _anagraficheHelper.AdattaPersonaFisica(richiedente)
			};

			_dettaglioPratica.richiedente = datiRichiedente;
		}
	}
}

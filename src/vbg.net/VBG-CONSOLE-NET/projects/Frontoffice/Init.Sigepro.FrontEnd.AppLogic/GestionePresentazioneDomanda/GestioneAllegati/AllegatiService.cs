using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati
{
	public class AllegatiService
	{
		ISalvataggioDomandaStrategy _salvataggioStrategy;

		public AllegatiService(ISalvataggioDomandaStrategy salvataggioStrategy)
		{
			this._salvataggioStrategy = salvataggioStrategy;
		}

		public void ModificaNomeFileEFlagFirmaDaCodiceOggetto(int idDomanda, int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
		{
			var domanda = this._salvataggioStrategy.GetById(idDomanda);

			domanda.WriteInterface.Allegati.ModificaNomeFileEFlagFirmaDaCodiceOggetto(codiceOggetto, nomeFile, firmatoDigitalmente);


			this._salvataggioStrategy.Salva(domanda);
		}
	}
}

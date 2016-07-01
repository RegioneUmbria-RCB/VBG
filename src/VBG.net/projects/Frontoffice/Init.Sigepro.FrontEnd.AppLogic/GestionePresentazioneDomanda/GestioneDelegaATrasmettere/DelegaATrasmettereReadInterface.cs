using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
	public class DelegaATrasmettereReadInterface: IDelegaATrasmettereReadInterface
	{
		bool _richiesta = false;
		AllegatoDellaDomanda _allegato = null;

		#region IDelegaATrasmettereReadInterface Members

		public bool Richiesta
		{
			get { return _richiesta; }
		}

		public AllegatoDellaDomanda Allegato
		{
			get { return this._allegato; }
		}

		#endregion

		public DelegaATrasmettereReadInterface(string codiceFiscalerichiedente, IAnagraficheReadInterface anagraficheReadInterface, PresentazioneIstanzaDbV2 database)
		{
			var codicifiscaliRichiedenti = anagraficheReadInterface.GetRichiedenti().Select( x => x.Codicefiscale.ToUpperInvariant());

			if (!codicifiscaliRichiedenti.Contains( codiceFiscalerichiedente.ToUpperInvariant() ))
				this._richiesta = true;

			if (database.DelegaATrasmettere.Count > 0 && !database.DelegaATrasmettere[0].IsIdAllegatoNull())
			{
				var idAllegato = database.DelegaATrasmettere[0].IdAllegato;
				var rigaAllegati = database.Allegati.FindById( idAllegato );

				this._allegato = new AllegatoDellaDomanda(rigaAllegati);
			}
		}



	}
}

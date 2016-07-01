using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
	public interface IDelegaATrasmettereReadInterface
	{
		bool Richiesta { get; }
		AllegatoDellaDomanda Allegato { get; }
	}
}

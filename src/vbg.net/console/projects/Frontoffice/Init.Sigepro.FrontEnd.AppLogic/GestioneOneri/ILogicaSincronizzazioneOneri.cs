// -----------------------------------------------------------------------
// <copyright file="ILogicaSincronizzazioneOneri.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	public interface ILogicaSincronizzazioneOneri
	{
		void SincronizzaOneri(DomandaOnline domanda);
	}
}

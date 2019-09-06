// -----------------------------------------------------------------------
// <copyright file="IStcService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.STC
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IStcService
	{
		NotificaAttivitaResponse NotificaAttivita(NotificaAttivitaRequest request, Action<SportelloType> modificaSportelloDestinatario = null);
        InserimentoPraticaResponse InserimentoPratica(InserimentoPraticaRequest request, string pecSportello, SportelloType sportelloDestinatario = null);
		bool PraticaEsisteNelBackend(string idPratica);
		RichiestaPraticheListaResponse RichiestaPraticheLista(RichiestaPraticheListaRequest richiesta);
		RichiestaPraticaResponse RichiestaPratica(string idPratica);
		AllegatoBinarioResponse AllegatoBinario(string codiceOggetto);
	}
}

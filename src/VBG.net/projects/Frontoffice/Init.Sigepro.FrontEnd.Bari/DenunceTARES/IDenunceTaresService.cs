// -----------------------------------------------------------------------
// <copyright file="IDenunceTaresService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IDenunceTaresService
	{
		DatiAnagraficiContribuenteDenunciaTares GetUtenze(DatiOperatore operatore, DatiUtenza utenza);
		bool IsContribuenteEsistente(DatiOperatore operatore, DatiUtenza utenza);
	}
}

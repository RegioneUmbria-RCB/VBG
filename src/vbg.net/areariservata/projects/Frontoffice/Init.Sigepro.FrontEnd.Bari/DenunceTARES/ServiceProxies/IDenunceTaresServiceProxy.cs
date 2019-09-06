// -----------------------------------------------------------------------
// <copyright file="IDenunceTaresServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IDenunceTaresServiceProxy
	{
		datiContribuenteResponseType GetUtenze(DatiOperatore operatore, DatiUtenza utenza);
		bool IsContribuenteEsistente(DatiOperatore operatore, DatiUtenza utenza);
	}
}

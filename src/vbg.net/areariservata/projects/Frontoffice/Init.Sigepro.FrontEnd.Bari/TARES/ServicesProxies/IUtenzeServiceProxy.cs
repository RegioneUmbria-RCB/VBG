// -----------------------------------------------------------------------
// <copyright file="IUtenzeServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;

	public interface IUtenzeServiceProxy
	{
		IEnumerable<DatiContribuenteDto> GetUtenzeByCodiceFiscale(string codiceFiscaleCaf, string indirizzoPec, string codiceFiscale);
		IEnumerable<DatiContribuenteDto> GetUtenzeByIdentificativoUtenza(string codiceFiscaleCaf, string indirizzoPec, int identificativoUtenza);
	}
}

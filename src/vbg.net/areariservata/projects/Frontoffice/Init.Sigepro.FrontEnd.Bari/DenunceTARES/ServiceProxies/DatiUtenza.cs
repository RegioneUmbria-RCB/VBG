// -----------------------------------------------------------------------
// <copyright file="DatiUtenza.cs" company="">
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
	using System.Text.RegularExpressions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiUtenza
	{
		public readonly int IdentificativoContribuente;
		public readonly string CodiceFiscaleOPartitIva;
		public readonly ItemChoiceType4 Tipodato;
		public readonly ItemChoiceType5 Tipodato2;

		public DatiUtenza(int identificativoContribuente, string codiceFiscaleOPartitIva)
		{
			this.IdentificativoContribuente = identificativoContribuente;
			this.CodiceFiscaleOPartitIva = codiceFiscaleOPartitIva;
			this.Tipodato = Regex.IsMatch(codiceFiscaleOPartitIva, "[a-zA-Z]{6}[0-9]{2}[a-zA-Z]{1}[0-9]{2}[a-zA-Z]{1}[0-9]{3}[a-zA-Z]{1}") ? ItemChoiceType4.codiceFiscale : ItemChoiceType4.partitaIva;
			this.Tipodato2 = this.Tipodato == ItemChoiceType4.codiceFiscale ? ItemChoiceType5.codiceFiscale : ItemChoiceType5.partitaIva;
		}
	}
}

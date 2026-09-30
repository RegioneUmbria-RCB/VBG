// -----------------------------------------------------------------------
// <copyright file="StatoModelloSalvato.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.ServerScriptService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class StatoModelloSalvato : StatoModello
	{
		public bool modelloSalvato { get; set; }

		internal StatoModelloSalvato()
		{
		}

		public StatoModelloSalvato(StatoModello sm, bool modelloSalvato = false)
		{
			this.modelloSalvato = modelloSalvato;
			this.campiNonVisibili = sm.campiNonVisibili;
			this.campiVisibili = sm.campiVisibili;
			this.errori = sm.errori;
			this.modifiche = sm.modifiche;
		}
	}
}

// -----------------------------------------------------------------------
// <copyright file="ModelloDinamicoOrdinato.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ModelloDinamicoOrdinato
	{
		public readonly int Ordine;
		public readonly ModelloDinamico Modello;

		public ModelloDinamicoOrdinato(int ordine, ModelloDinamico modello)
		{
			this.Ordine = ordine;
			this.Modello = modello;
		}

	}
}

// -----------------------------------------------------------------------
// <copyright file="EliminaValoriCampo.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EliminaValoriCampo : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdCampoDinamico;

		public EliminaValoriCampo(string idComune, int idMovimento, int idCampoDinamico)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdCampoDinamico = idCampoDinamico;
		}
	}
}

// -----------------------------------------------------------------------
// <copyright file="TrasmettiMovimento.cs" company="">
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
	public class TrasmettiMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;

		public TrasmettiMovimento(string idComune , int idMovimento)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
		}
	}
}

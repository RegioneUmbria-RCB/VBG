// -----------------------------------------------------------------------
// <copyright file="CreaMovimento.cs" company="">
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
	/// Comando da utilizzare per creare un nuovo movimento di frontoffice
	/// </summary>
	public class CreaMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimentoDaEffettuare;
		public readonly int IdMovimentoOrigine;

		public CreaMovimento(string idComune,int idMovimentoDaEffettuare,int idMovimentoOrigine)
		{
			this.IdComune = idComune;
			this.IdMovimentoDaEffettuare = idMovimentoDaEffettuare;
			this.IdMovimentoOrigine = idMovimentoOrigine;
		}
	}
}

// -----------------------------------------------------------------------
// <copyright file="DatiProtocolloMovimento.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// Dati relativi alla protocollazione di un movimento o dell'istanza a cui appartiene
	/// </summary>
	public class DatiProtocolloMovimento
	{
		public string Numero { get; set; }
		public DateTime? Data { get; set; }

		public bool DatiPresenti { get { return !String.IsNullOrEmpty(this.Numero); } }
	}
}

// -----------------------------------------------------------------------
// <copyright file="RiferimentiIstanza.cs" company="">
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
	/// Riferimenti dell'istanza collegata ad un movimento
	/// </summary>
	public class RiferimentiIstanza
	{
		public string IdComune { get; set; }
		public int CodiceIstanza { get; set; }
		public string NumeroIstanza { get; set; }
		public DateTime DataIstanza { get; set; }
		public DatiProtocolloMovimento Protocollo { get; set; }
	}
}

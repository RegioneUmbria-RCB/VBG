// -----------------------------------------------------------------------
// <copyright file="TipoPagamento.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class TipoPagamento
	{
		public string Codice { get; private set; }
		public string Descrizione { get; private set; }

		public TipoPagamento(string codice, string descrizione)
		{
			this.Codice = codice;
			this.Descrizione = descrizione;
		}
	}
}

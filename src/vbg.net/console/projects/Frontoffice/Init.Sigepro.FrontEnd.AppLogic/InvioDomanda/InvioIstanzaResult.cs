// -----------------------------------------------------------------------
// <copyright file="InvioIstanzaResult.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;



	public partial class InvioIstanzaResult
	{
		public enum TipoEsitoInvio
		{
			InvioRiuscito,
			InvioFallito,
			InserimentoFallito,
			InvioRiuscitoNoBackend
		}

		public TipoEsitoInvio Esito { get; private set; }
		public string CodiceIstanza { get; private set; }
		public string NumeroIstanza { get; private set; }

		public static InvioIstanzaResult InvioFallito()
		{
			return new InvioIstanzaResult(TipoEsitoInvio.InvioFallito, String.Empty, string.Empty);
		}

		public static InvioIstanzaResult InserimentoFallito()
		{
			return new InvioIstanzaResult(TipoEsitoInvio.InserimentoFallito, String.Empty, string.Empty);
		}

		public static InvioIstanzaResult InvioRiuscito(string codiceIstanza, string numeroIstanza)
		{
			return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscito, codiceIstanza, numeroIstanza);
		}

		public static InvioIstanzaResult InvioRiuscitoNoBackend(string codiceIstanza, string numeroIstanza)
		{
			return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscitoNoBackend, codiceIstanza, numeroIstanza);
		}

		protected InvioIstanzaResult(TipoEsitoInvio esito, string codiceIstanza, string numeroistanza)
		{
			this.Esito = esito;
			this.CodiceIstanza = codiceIstanza;
			this.NumeroIstanza = numeroistanza;
		}
	}
}

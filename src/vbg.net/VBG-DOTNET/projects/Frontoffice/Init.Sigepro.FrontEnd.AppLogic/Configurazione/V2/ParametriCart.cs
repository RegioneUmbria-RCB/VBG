// -----------------------------------------------------------------------
// <copyright file="ParametriCart.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ParametriCart : IParametriConfigurazione
	{
		public readonly string UrlCompletoApplicazioneFacct;

		public bool IsUrlCompletoApplicazioneFacctDefined
		{
			get { return !String.IsNullOrEmpty(this.UrlCompletoApplicazioneFacct); }
		}

        public readonly string UrlAccettatore;

		internal ParametriCart(string urlCompletoApplicazioneCart, string urlAccettatore)
		{
			this.UrlCompletoApplicazioneFacct = urlCompletoApplicazioneCart;
            this.UrlAccettatore = urlAccettatore;
		}
	}
}

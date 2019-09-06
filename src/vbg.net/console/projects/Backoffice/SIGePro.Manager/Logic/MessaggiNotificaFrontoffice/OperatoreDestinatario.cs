// -----------------------------------------------------------------------
// <copyright file="OperatoreDestinatario.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class OperatoreDestinatario : IDestinatarioMessaggioNotifica
	{
		string _email;
		string _codiceFiscale;

		public string Email
		{
			get { return this._email; }
		}

		public string CodiceFiscale
		{
			get { return this._codiceFiscale; }
		}

		public static IDestinatarioMessaggioNotifica Fittizio()
		{
			return new OperatoreDestinatario
			{
				_codiceFiscale = String.Empty,
				_email = String.Empty
			};
		}

		private OperatoreDestinatario()
		{
		}

		protected OperatoreDestinatario(Responsabili operatore)
		{
			this._email = operatore.EMAIL;
			this._codiceFiscale = operatore.CODICEFISCALE;
		}
	}
}

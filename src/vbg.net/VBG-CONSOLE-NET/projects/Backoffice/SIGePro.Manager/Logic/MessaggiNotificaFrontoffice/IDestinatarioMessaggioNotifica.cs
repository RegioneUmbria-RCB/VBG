// -----------------------------------------------------------------------
// <copyright file="IDestinatarioMessaggioNotifica.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IDestinatarioMessaggioNotifica
	{
		string Email { get; }
		string CodiceFiscale { get; }
	}
}

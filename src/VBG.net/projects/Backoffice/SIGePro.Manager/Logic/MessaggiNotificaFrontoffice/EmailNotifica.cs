// -----------------------------------------------------------------------
// <copyright file="EmailNotifica.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Manager.Logic.SmtpMail;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EmailNotifica: IMessaggioNotifica
	{
		string _oggetto;
		string _corpo;
		IDestinatarioMessaggioNotifica _destinatario;
		ISmtpService _smtpService;

		public EmailNotifica(string oggetto, string corpo, IDestinatarioMessaggioNotifica destinatario, ISmtpService smtpService)
		{
			this._oggetto = oggetto;
			this._corpo = corpo;
			this._destinatario = destinatario;
			this._smtpService = smtpService;
		}

		public void Send()
		{
			if (String.IsNullOrEmpty(this._destinatario.Email))
			{
				return;
			}

			var msg = new SIGeProMailMessage
			{
				CorpoMail = this._corpo,
				Oggetto = this._oggetto,
				InviaComeHtml = true,
				Destinatari = this._destinatario.Email
			};

			this._smtpService.Send(msg);
		}
	}
}

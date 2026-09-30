// -----------------------------------------------------------------------
// <copyright file="MessaggioNotificaFactory.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice.IstanzaRicevuta;
using Init.SIGePro.Manager.Logic.SmtpMail;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MessaggioNotificaFactory
	{
		IEnumerable<IDestinatarioMessaggioNotifica> _destinatari;
		CertificatoDiInvio.CertificatoDiInvioElaborato _messaggio;
		ISmtpService _smtpService;

		public MessaggioNotificaFactory(IEnumerable<IDestinatarioMessaggioNotifica> destinatari, CertificatoDiInvio.CertificatoDiInvioElaborato messaggio, ISmtpService smtpService)
		{
			this._destinatari = destinatari;
			this._messaggio = messaggio;
			this._smtpService = smtpService;
		}

		public IEnumerable<IMessaggioNotifica> Create(FlagsTipoMessaggioNotifica tipoMessaggio)
		{
			if (tipoMessaggio.InviaEmail)
			{
				return this._destinatari.Select( x => new EmailNotifica( this._messaggio.Titolo, this._messaggio.Corpo, x, this._smtpService));
			}

			if (tipoMessaggio.InviaMessaggio)
			{
				throw new Exception("E'supportata solamente la notifica tramite email");
			}

			return Enumerable.Empty<IMessaggioNotifica>();
		}
	}
}

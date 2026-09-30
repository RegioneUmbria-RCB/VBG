// -----------------------------------------------------------------------
// <copyright file="EmailNotificaTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;
using Init.SIGePro.Manager.Logic.SmtpMail;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class EmailNotificaTests
	{
		public class SmtpServiceStub : ISmtpService
		{
			public SIGeProMailMessage MessaggioDaInviare { get; private set; }

			public void Send(SIGeProMailMessage msg)
			{
				this.MessaggioDaInviare = msg;
			}
		}

		public class DestinatarioMessaggioStub : IDestinatarioMessaggioNotifica
		{
			public string Email { get; set; }

			public string CodiceFiscale { get; set; }
		}


		[TestMethod]
		public void il_servizio_di_invio_mail_viene_invocato_con_i_valori_di_inizializzazione()
		{
			var oggetto = "Oggetto";
			var corpo = "Corpo";
			var destinatario = new DestinatarioMessaggioStub { Email = "email", CodiceFiscale = "CF"};
			var svc = new SmtpServiceStub();
			var email = new EmailNotifica(oggetto, corpo, destinatario, svc);

			email.Send();

			Assert.IsNotNull(svc.MessaggioDaInviare);
			Assert.AreEqual<string>(oggetto, svc.MessaggioDaInviare.Oggetto);
			Assert.AreEqual<string>(corpo, svc.MessaggioDaInviare.CorpoMail);
			Assert.AreEqual<string>(destinatario.Email, svc.MessaggioDaInviare.Destinatari);
			Assert.IsTrue(svc.MessaggioDaInviare.InviaComeHtml);
		}

		[TestMethod]
		public void il_servizio_di_invio_mail_non_viene_invocato_se_il_destinatario_non_ha_indirizzo_email()
		{
			var oggetto = "Oggetto";
			var corpo = "Corpo";
			var destinatario = new DestinatarioMessaggioStub { Email = "", CodiceFiscale = "CF" };
			var svc = new SmtpServiceStub();
			var email = new EmailNotifica(oggetto, corpo, destinatario, svc);

			email.Send();

			Assert.IsNull(svc.MessaggioDaInviare);
		}

	}
}

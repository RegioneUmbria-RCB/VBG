using System;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Net.Mail;
using System.IO;
using System.Net;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;
using Init.SIGePro.Utils;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Authentication;
using System.Collections.Generic;
using Init.Utils;
using System.ComponentModel;
using Init.SIGePro.Manager.Logic.SmtpMail;
using Sigepro.net.WebServices.WsSIGePro;

namespace SIGePro.Net.WebServices.WSSIGeProSmtpMail
{
	/// <summary>
	/// Summary description for SmtpMailSender
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class SmtpMailSender : SigeproWebService
	{
		[WebMethod]
		public void Send(string token, string software, SIGeProMailMessage message)
		{
			// Autenticazione
			AuthenticationInfo authInfo = CheckToken(token);

			using (DataBase database = authInfo.CreateDatabase())
			{
				new SmtpSender().InviaEmail(database, authInfo.IdComune, software, message);
			}
		}

	}

}
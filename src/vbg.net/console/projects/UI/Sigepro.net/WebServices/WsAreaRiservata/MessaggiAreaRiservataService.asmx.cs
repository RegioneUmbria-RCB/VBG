using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using System.Collections.Generic;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for MessaggiAreaRiservata
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class MessaggiAreaRiservataService : System.Web.Services.WebService
	{
		[WebMethod]
		public void CreaMessaggioDomanda(string token, int idDomanda, string contesto, string codiceFiscaleMittente, string mittente, string istanzaSerializzata)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			new FoMessaggiMgr(authInfo.CreateDatabase(), authInfo.IdComune).CreaMessaggioDomanda(idDomanda, contesto, codiceFiscaleMittente, mittente, istanzaSerializzata);
		}

		[WebMethod]
		public void CreaMessaggioDomandaRicevuta(string token, int idDomandaFrontoffice, int codiceIstanza)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			// new FoMessaggiMgr(authInfo.CreateDatabase()).CreaMessaggioDomandaRicevuta(authInfo.IdComune, idDomandaFrontoffice, codiceIstanza);
			new FoMessaggiMgr(authInfo.CreateDatabase(), authInfo.IdComune).InviaNotificaRicezioneDomandaAreaRiservata(codiceIstanza);
		}

		[WebMethod]
		public void InserisciMessaggio(string token, string software, int? idDomanda, string codFiscaleDestinatario, string mittente, string oggetto, string corpo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken( token );

			if(authInfo == null)
				throw new InvalidTokenException( token );

			FoMessaggi msg = new FoMessaggi();
			msg.Idcomune = authInfo.IdComune;
			msg.Software = software;
			msg.Codicedomanda = idDomanda;
			msg.Mittente = mittente;
			msg.Codicefiscaledestinatario = codFiscaleDestinatario;
			msg.Oggetto = oggetto;
			msg.Corpo = corpo;
			msg.Data = DateTime.Now;
			msg.FlgLetto = 0;

			new FoMessaggiMgr(authInfo.CreateDatabase()).Insert(msg);
		}


		[WebMethod]
		public List<FoMessaggi> GetMessaggi(string token, string software, string codiceFiscale, int? statoLettura)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			FoMessaggi filtro = new FoMessaggi();
			filtro.Idcomune = authInfo.IdComune;
			filtro.Software = software;
			filtro.Codicefiscaledestinatario = codiceFiscale;
			filtro.FlgLetto = statoLettura;
			filtro.OrderBy = "id desc";

			return new FoMessaggiMgr(authInfo.CreateDatabase()).GetList(filtro);
		}

		[WebMethod]
		public FoMessaggi GetMessaggio(string token, int idMessaggio)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			FoMessaggi msg = new FoMessaggiMgr(authInfo.CreateDatabase()).GetById( authInfo.IdComune , idMessaggio );

			if (msg == null)
				throw new ArgumentException("Messaggio con id " + idMessaggio.ToString() + " non trovato");

			msg.FlgLetto = 1;

			new FoMessaggiMgr(authInfo.CreateDatabase()).Update(msg);

			return msg;
		}
	}
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public void CreaMessaggioDomanda(string token, int idDomanda, string contesto, string codiceFiscaleMittente, string mittente, string istanzaSerializzata)
		{
			new MessaggiAreaRiservataService().CreaMessaggioDomanda(token, idDomanda, contesto, codiceFiscaleMittente, mittente, istanzaSerializzata);
		}

		[WebMethod]
		public void CreaMessaggioDomandaRicevuta(string token, int idDomandaFrontoffice, int codiceIstanza)
		{
			new MessaggiAreaRiservataService().CreaMessaggioDomandaRicevuta(token, idDomandaFrontoffice, codiceIstanza);
		}

		[WebMethod]
		public void InserisciMessaggio(string token, string software, int? idDomanda, string codFiscaleDestinatario, string mittente, string oggetto, string corpo)
		{
			new MessaggiAreaRiservataService().InserisciMessaggio(token, software, idDomanda, codFiscaleDestinatario, mittente, oggetto, corpo);
		}


		[WebMethod]
		public List<FoMessaggi> GetMessaggi(string token, string software, string codiceFiscale, int? statoLettura)
		{
			return new MessaggiAreaRiservataService().GetMessaggi(token, software, codiceFiscale, statoLettura);
		}

		[WebMethod]
		public FoMessaggi GetMessaggio(string token, int idMessaggio)
		{
			return new MessaggiAreaRiservataService().GetMessaggio(token, idMessaggio);
		}
	}
}

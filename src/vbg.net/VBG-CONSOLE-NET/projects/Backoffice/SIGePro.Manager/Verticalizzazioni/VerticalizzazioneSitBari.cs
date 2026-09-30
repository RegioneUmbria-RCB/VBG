using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
	public partial class VerticalizzazioneSitBari : Verticalizzazione
	{
		private static class Constants
		{
			public static string NomeVerticalizzazione =  "SIT_BARI";
			public static string AttributoCodEnte = "COD_ENTE";
			public static string AttributoRequestFrom = "REQUEST_FROM";
			public static string AttributoTipoIndirizzoCercato = "TIPO_INDIRIZZO_CERCATO";
			public static string AttributoWsUrl = "WS_URL_VALIDAZIONE_CIVICO";
		}

		public VerticalizzazioneSitBari(string idComune, string software) : base(idComune, Constants.NomeVerticalizzazione, software) 
        { 
        
        
        }

		public string CodEnte
		{
			get { return GetString(Constants.AttributoCodEnte); }
			set { SetString(Constants.AttributoCodEnte, value); }
		}


		public string RequestFrom
		{
			get { return GetString(Constants.AttributoRequestFrom); }
			set { SetString(Constants.AttributoRequestFrom, value); }
		}

		public string TipoIndirizzoCercato
		{
			get { return GetString(Constants.AttributoTipoIndirizzoCercato); }
			set { SetString(Constants.AttributoTipoIndirizzoCercato, value); }
		}

		public string WsUrlValidazioneCivico
		{
			get { return GetString(Constants.AttributoWsUrl); }
			set { SetString(Constants.AttributoWsUrl, value); }
		}
	}
}
